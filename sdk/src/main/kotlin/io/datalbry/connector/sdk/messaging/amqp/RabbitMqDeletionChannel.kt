package io.datalbry.connector.sdk.messaging.amqp

import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.NodeReference
import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitMqDeletionChannel(
    props: ConnectorProperties,
    private val rabbitMq: RabbitTemplate
): Channel<NodeReference> {
    private val channel = "${props.alxndria.datasource}-${Channel.DESTINATION_NODE_DELETION}"

    override fun propagate(message: NodeReference) {
        rabbitMq.convertAndSend(channel, message)
    }
}
