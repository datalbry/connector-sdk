package io.datalbry.connector.sdk.messaging.jms

import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.messaging.Channel
import org.springframework.jms.core.JmsTemplate

class JmsAddChannel(
    props: ConnectorProperties,
    private val jmsTemplate: JmsTemplate
)
    : Channel<DocumentEdge>
{

    private val channel = "${props.alxndria.datasource}-${Channel.DESTINATION_NODE_ADDITION}"

    override fun propagate(message: DocumentEdge) {
        val headers = message.headers.toMutableMap()
        headers["_type"] = DocumentEdge::class.qualifiedName.toString()
        val enrichedMessage = message.copy(headers = headers)
        jmsTemplate.convertAndSend(channel, enrichedMessage)
    }

    override fun hasElement(): Boolean {
        return jmsTemplate.browse(channel) { _, browser -> browser.enumeration.hasMoreElements() } ?: false
    }
}
