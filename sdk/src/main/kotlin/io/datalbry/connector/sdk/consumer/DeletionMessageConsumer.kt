package io.datalbry.connector.sdk.consumer

import io.datalbry.alxndria.client.api.IndexClient
import io.datalbry.connector.api.CrawlProcessor
import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.ConnectorDocumentState
import io.datalbry.connector.sdk.state.NodeReference
import io.datalbry.precise.api.schema.document.Document
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.annotation.JmsListener

/**
 * The [DeletionMessageConsumer] contains the logic on how to consume JMS messages (in form of [NodeReference])
 *
 * In contrast to the [AdditionMessageConsumer], the [DeletionMessageConsumer] consumes [NodeReference] and
 * deletes the corresponding [Document]s as well as propagating the deletions of further Nodes by their [NodeReference].
 *
 * @param index client to delete the documents with
 * @param channel to publish further deletion of nodes to
 * @param state of the connector, used for deletions of document
 *
 * @author timo gruen - 2020-11-15
 */
class DeletionMessageConsumer(
    private val index: IndexClient,
    private val channel: Channel<NodeReference>,
    private val state: ConnectorDocumentState
) {
    @Value("\${io.datalbry.datasource.key}") lateinit var datasourceKey: String

    @JmsListener(destination = "\${io.datalbry.datasource.key}-${Channel.DESTINATION_NODE_DELETION}")
    fun consume(node: NodeReference) {
        val lock = state.lock(node)

        try {
            state.getUnseenDocuments(node, lock).forEach {
                index.deleteDocument(datasourceKey, it)
                state.remove(node, it, lock)
            }

            state.getUnseenNodes(node, lock).forEach {
                channel.propagate(it)
                state.remove(node, it, lock)
            }

            state.remove(node, lock)
            state.release(node, lock)

        } catch (e: Throwable) {
            log.warn("Exception[${e.javaClass.simpleName}] thrown while processing $node.")
            log.trace("", e)
            state.release(node, lock)
            if (e is Error) throw e
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeletionMessageConsumer::class.java)
    }
}
