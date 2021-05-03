package io.datalbry.connector.autoconfigure.amqp

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.connector.autoconfigure.consumer.MessageConsumerAutoConfiguration
import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer
import io.datalbry.connector.sdk.consumer.DeletionMessageConsumer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@ConditionalOnClass(AdditionMessageConsumer::class, DeletionMessageConsumer::class)
@AutoConfigureAfter(MessageConsumerAutoConfiguration::class)
open class AmqpAutoConfiguration {

    @Bean
    open fun jacksonMessageConverter(): Jackson2JsonMessageConverter {
        val mapper = jacksonObjectMapper()
        return Jackson2JsonMessageConverter(mapper)
    }

}
