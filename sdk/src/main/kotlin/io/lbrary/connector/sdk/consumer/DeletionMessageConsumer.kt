package io.lbrary.connector.sdk.consumer

import io.lbrary.client.api.IndexClient
import io.lbrary.connector.sdk.messaging.Channel
import io.lbrary.connector.sdk.state.ConnectorDocumentState
import io.lbrary.connector.sdk.state.NodeReference
import io.lbrary.service.index.api.datasource.DatasourceId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.annotation.JmsListener

class DeletionMessageConsumer(
    private val index: IndexClient,
    private val channel: Channel<NodeReference>,
    private val state: ConnectorDocumentState
) {
    @Value("\${io.lbrary.datasource.key}") lateinit var datasourceKey: String

    @JmsListener(destination = "\${io.lbrary.datasource.key}-${Channel.DESTINATION_NODE_DELETION}")
    fun consume(node: NodeReference) {
        val lock = state.lock(node)

        try {
            state.getUnseenDocuments(node, lock).forEach {
                index.deleteDocument(DatasourceId(datasourceKey), it)
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
