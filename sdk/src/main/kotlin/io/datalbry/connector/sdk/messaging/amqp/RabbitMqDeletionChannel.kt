package io.datalbry.connector.sdk.messaging.amqp

import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.NodeReference
import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitMqDeletionChannel(private val rabbitMq: RabbitTemplate): Channel<NodeReference> {
    private val topic = Channel.DESTINATION_NODE_DELETION
    private val routing = Channel.DESTINATION_NODE_ADDITION

    override fun propagate(message: NodeReference) {
        rabbitMq.convertAndSend(topic, routing, message)
    }
}
