package io.datalbry.connector.sdk.consumer

import io.datalbry.alxndria.client.api.IndexClient
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.connector.api.CrawlProcessor
import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer.Companion.CHECKSUM_FIELD
import io.datalbry.connector.sdk.extension.toDocumentState
import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.ConnectorDocumentState
import io.datalbry.connector.sdk.state.Lock
import io.datalbry.connector.sdk.state.NodeReference
import io.datalbry.precise.api.schema.document.generic.GenericDocument
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.annotation.JmsListener

/**
 * The [AdditionMessageConsumer] contains the logic on how to consume JMS messages (in form of [DocumentEdge])
 *
 * Internally relying on a single [CrawlProcessor] of type [DocumentEdge] and [Document].
 * By default we are using the [io.datalbry.connector.sdk.consumer.generic.GenericCrawlProcessor] which is able to
 * map [io.datalbry.connector.sdk.consumer.generic.GenericCrawlProcessor] to the non generic type required by the
 * connector sdk.
 *
 * There are a few things to mention.
 *
 * - If the connector-sdk fails while processing documents, the state might not be in sync with the index
 *
 * - Deletions will be propagated to the internal state, right after the document has been deleted from the index.
 *   This strategy results into not knowing of already deleted items if the connector fails right after
 *   deleting an item in the index, but before putting the update into the state.
 *
 * - Additions will be propagated to the internal state, right before putting the document into the index.
 *   This strategy results into having documents in the state, but not in the index. Which will take place
 *   if the connector-sdk fails in between propagating the state and putting the document.
 *
 * @param index client which is being used to put documents into the index
 * @param processor to process the [DocumentEdge]s with and to get the resulting [Document]s from
 * @param deletionChannel channel to put the deletion ([DocumentEdge] / [NodeReference]) candidates to
 * @param addChannel channel to put the [DocumentEdge] to
 * @param state to persist the whole traversal with
 *
 * @author timo gruen - 2020-11-15
 */
class AdditionMessageConsumer(
    private val index: IndexClient,
    private val processor: CrawlProcessor<DocumentEdge, Document>,
    private val deletionChannel: Channel<NodeReference>,
    private val addChannel: Channel<DocumentEdge>,
    private val state: ConnectorDocumentState
) {

    @Value("\${io.datalbry.datasource.key}") lateinit var datasourceKey: String

    @JmsListener(destination = "\${io.datalbry.datasource.key}-${Channel.DESTINATION_NODE_ADDITION}", concurrency = "1")
    fun consume(edge: DocumentEdge) {
        val node = NodeReference(edge.uuid)
        val lock = state.lock(node)

        try {
            log.trace("Start processing Edge[${edge.uuid}]")
            val item = processor.process(edge)

            item.objects.forEach {
                log.trace("Processing Document[${it.type}][${it.id}]")
                val hasChanged = hasChanged(node, it, lock)
                state.put(node, it.toDocumentState(), lock)
                if (hasChanged) index.putDocument(datasourceKey, it.removeChecksum())
                log.trace("Completed processing Document[${it.type}][${it.id}]")
            }

            state.getUnseenDocuments(node, lock).forEach {
                log.trace("Deleting Document[${it}]")
                index.deleteDocument(datasourceKey, it)
                state.remove(node, it, lock)
                log.trace("Completed deleting Document[$it]")
            }

            item.edges.forEach {
                log.trace("Discovered Edge[${it.uuid}]")
                state.put(node, NodeReference(it.uuid), lock)
                addChannel.propagate(it)
            }

            state.getUnseenNodes(node, lock).forEach {
                log.trace("Unseen Node[${it.uuid}]")
                deletionChannel.propagate(it)
                state.remove(node, it, lock)
            }
        } catch (e: Throwable) {
            log.warn(
                "Failed to process Node[${node.uuid}] due to an Exception[\"${e.message}\"]. " +
                        "Check trace for further information.")
            log.trace("", e)
            if (e is Error) throw e
        } finally {
            state.release(node, lock)
            log.trace("Completed Edge[${edge.uuid}]")
        }
    }

    private fun hasChanged(node: NodeReference, doc: Document, lock: Lock): Boolean {
        val checksum = state.getChecksum(node, doc.id, lock)
        return checksum.isEmpty() || checksum != doc[CHECKSUM_FIELD].value
    }

    companion object {
        const val CHECKSUM_FIELD = "_checksum"
        private val log = LoggerFactory.getLogger(AdditionMessageConsumer::class.java)
    }
}

private fun Document.removeChecksum(): Document {
    val id = this.id
    val type = this.type
    val fields = this.fields.toMutableSet()
    fields.removeIf { it.name == CHECKSUM_FIELD }
    return GenericDocument(type, id, fields)
}
