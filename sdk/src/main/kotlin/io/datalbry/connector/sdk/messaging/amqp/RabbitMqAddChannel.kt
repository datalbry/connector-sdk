package io.datalbry.connector.sdk.messaging.amqp

import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.messaging.Channel
import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitMqAddChannel(private val rabbitMq: RabbitTemplate) : Channel<DocumentEdge> {
    private val topic = Channel.DESTINATION_NODE_ADDITION

    override fun propagate(message: DocumentEdge) {
        val headers = message.headers.toMutableMap()
        headers["_type"] = DocumentEdge::class.qualifiedName.toString()
        val enrichedMessage = message.copy(headers = headers)
        rabbitMq.convertAndSend(topic, enrichedMessage)
    }
}
