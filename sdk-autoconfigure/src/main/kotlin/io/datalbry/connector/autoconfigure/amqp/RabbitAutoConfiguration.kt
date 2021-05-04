package io.datalbry.connector.autoconfigure.amqp

import io.datalbry.connector.autoconfigure.consumer.MessageConsumerAutoConfiguration
import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer
import io.datalbry.connector.sdk.consumer.DeletionMessageConsumer
import io.datalbry.connector.sdk.messaging.Channel
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(AdditionMessageConsumer::class, DeletionMessageConsumer::class)
@AutoConfigureBefore(MessageConsumerAutoConfiguration::class)
class RabbitAutoConfiguration {

    @Value(AdditionMessageConsumer.DESTINATION) lateinit var additionMessageChannel: String
    @Value(DeletionMessageConsumer.DESTINATION) lateinit var deletionMessageChannel: String

    @Bean open fun additionQueue() = Queue(additionMessageChannel, true)

    @Bean open fun deletionQueue() = Queue(deletionMessageChannel, true)

    @Bean open fun additionTopicExchange() = TopicExchange(Channel.DESTINATION_NODE_ADDITION)

    @Bean open fun deletionTopicExchange() = TopicExchange(Channel.DESTINATION_NODE_DELETION)

    @Bean open fun additionQueueBinding() = BindingBuilder
        .bind(additionQueue())
        .to(additionTopicExchange())
        .with(Channel.DESTINATION_NODE_ADDITION)!!

    @Bean open fun deletionQueueBinding() = BindingBuilder
        .bind(additionQueue())
        .to(additionTopicExchange())
        .with(Channel.DESTINATION_NODE_DELETION)!!
}
