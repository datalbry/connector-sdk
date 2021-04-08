package io.datalbry.connector.sdk.messaging.jms

import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.NodeReference
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate

class JmsDeletionChannel(
        private val jmsTemplate: JmsTemplate
): Channel<NodeReference> {

    @Value("io.lbrary.datasource.key") lateinit var datasourceKey: String

    override fun propagate(message: NodeReference) {
        jmsTemplate.convertAndSend(
            "${datasourceKey}-${Channel.DESTINATION_NODE_DELETION}",
            message)
    }

    override fun hasElement(): Boolean {
        return jmsTemplate.browse(
            "$datasourceKey-${Channel.DESTINATION_NODE_DELETION}"
        ) {
                _, browser -> browser.enumeration.hasMoreElements()
        } ?: false
    }

}
