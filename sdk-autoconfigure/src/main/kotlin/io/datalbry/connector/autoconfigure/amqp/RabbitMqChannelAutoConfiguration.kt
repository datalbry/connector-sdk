package io.datalbry.connector.autoconfigure.amqp

import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.messaging.amqp.RabbitMqAddChannel
import io.datalbry.connector.sdk.messaging.amqp.RabbitMqDeletionChannel
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(RabbitMqAddChannel::class, RabbitMqDeletionChannel::class)
@AutoConfigureAfter(AmqpAutoConfiguration::class)
open class RabbitMqChannelAutoConfiguration {

    @Bean
    open fun rabbitmqAddChannel(
        props: ConnectorProperties,
        rabbitmq: RabbitTemplate
    ): RabbitMqAddChannel {
        return RabbitMqAddChannel(props, rabbitmq)
    }

    @Bean
    open fun rabbitmqDeletionChannel(
        props: ConnectorProperties,
        rabbitmq: RabbitTemplate
    ): RabbitMqDeletionChannel {
        return RabbitMqDeletionChannel(props, rabbitmq)
    }

}
