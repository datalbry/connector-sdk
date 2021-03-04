package io.lbrary.connector.sdk.consumer

import io.lbrary.client.api.IndexClient
import io.lbrary.connector.api.CrawlProcessor
import io.lbrary.connector.api.DocumentEdge
import io.lbrary.connector.sdk.messaging.Channel
import io.lbrary.connector.sdk.state.ConnectorDocumentState
import io.lbrary.connector.sdk.state.NodeReference
import io.lbrary.service.index.api.datasource.DatasourceId
import io.lbrary.service.index.api.document.Document
import io.lbrary.service.index.api.validation.ErrorLevel
import io.lbrary.service.index.api.validation.ValidationError
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.annotation.JmsListener
import java.util.*

/**
 * The [AdditionMessageConsumer] contains the logic on how to consume JMS messages (in form of [DocumentEdge])
 *
 * Internally relying on a single [CrawlProcessor] of type [DocumentEdge] and [Document].
 * By default we are using the [io.lbrary.connector.sdk.consumer.generic.GenericCrawlProcessor] which is able to
 * map [io.lbrary.connector.sdk.consumer.generic.GenericCrawlProcessor] to the non generic type required by the
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
 * @param deletions channel to put the deletion ([DocumentEdge] / [NodeReference]) candidates to
 * @param additions channel to put the [DocumentEdge] to
 * @param state to persist the whole traversal with
 *
 * @author timo gruen - 2020-11-15
 */
class AdditionMessageConsumer(
    private val index: IndexClient,
    private val processor: CrawlProcessor<DocumentEdge, Document>,
    private val deletions: Channel<NodeReference>,
    private val additions: Channel<DocumentEdge>,
    private val state: ConnectorDocumentState
) {

    @Value("\${io.lbrary.datasource.key}") lateinit var datasourceKey: String

    @JmsListener(destination = "\${io.lbrary.datasource.key}-${Channel.DESTINATION_NODE_ADDITION}", concurrency = "1")
    fun consume(edge: DocumentEdge) {
        val node = NodeReference(edge.uuid)
        val lock = state.lock(node)

        try {
            log.trace("Start processing Edge[${edge.uuid}]")
            val documentNode = processor.process(edge)

            documentNode.objects.forEach {
                log.trace("Processing Document[${it.id.type.key}][${it.id.key}]")
                state.put(node, it.id, lock)
                val validation = index.putDocument(DatasourceId(datasourceKey), it)
                validation.forEach(this@AdditionMessageConsumer::logValidation)
            }

            state.getUnseenDocuments(node, lock).forEach {
                log.trace("Deleting Document[${it.key}][${it.type.key}]")
                index.deleteDocument(DatasourceId(datasourceKey), it)
                state.remove(node, it, lock)
            }

            documentNode.edges.forEach {
                log.trace("Discovered Edge[${it.uuid}]")
                state.put(node, NodeReference(it.uuid), lock)
                additions.propagate(it)
            }

            state.getUnseenNodes(node, lock).forEach {
                log.trace("Unseen Node[${it.uuid}]")
                deletions.propagate(it)
                state.remove(node, it, lock)
            }
            state.release(node, lock)
            log.trace("Successfully completed Edge[${edge.uuid}]")
        } catch (e: Throwable) {
            log.warn(
                "Failed to process Node[${node.uuid}] due to an Exception[\"${e.message}\"]. " +
                        "Check trace for further information.")
            log.trace("", e)
            state.release(node, lock)
            if (e is Error) throw e
        }
    }

    private fun logValidation(v: ValidationError) {
        when (v.level) {
            ErrorLevel.INFO -> log.info(v.message)
            ErrorLevel.WARN -> log.warn(v.message)
            ErrorLevel.FATAL -> log.warn(v.message)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(AdditionMessageConsumer::class.java)
    }
}
