package io.datalbry.connector.sdk.messaging.jms

import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.sdk.messaging.Channel
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate

class JmsAddChannel(
        private val jmsTemplate: JmsTemplate
)
    : Channel<DocumentEdge>
{

    @Value("\${io.lbrary.datasource.key}") lateinit var datasourceKey: String

    override fun propagate(message: DocumentEdge) {
        val headers = message.headers.toMutableMap()
        headers["_type"] = DocumentEdge::class.qualifiedName.toString()
        val enrichedMessage = message.copy(headers = headers)
        jmsTemplate.convertAndSend(
            "$datasourceKey-${Channel.DESTINATION_NODE_ADDITION}",
            enrichedMessage)
    }

    override fun hasElement(): Boolean {
        return jmsTemplate.browse(
            "$datasourceKey-${Channel.DESTINATION_NODE_ADDITION}"
        ) {
                _, browser -> browser.enumeration.hasMoreElements()
        } ?: false
    }
}
