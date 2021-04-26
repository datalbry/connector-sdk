package io.datalbry.connector.sdk.messaging.jms

import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.NodeReference
import org.springframework.jms.core.JmsTemplate

class JmsDeletionChannel(
    props: ConnectorProperties,
    private val jmsTemplate: JmsTemplate
): Channel<NodeReference> {

    private val channel = "${props.alxndria.datasource}-${Channel.DESTINATION_NODE_DELETION}"

    override fun propagate(message: NodeReference) {
        jmsTemplate.convertAndSend(channel, message)
    }

    override fun hasElement(): Boolean {
        return jmsTemplate.browse(channel) { _, browser -> browser.enumeration.hasMoreElements() } ?: false
    }
}
