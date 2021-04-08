package io.datalbry.connector.autoconfigure.jms

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer
import io.datalbry.connector.sdk.consumer.DeletionMessageConsumer
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageType


@Configuration
@ConditionalOnClass(AdditionMessageConsumer::class, DeletionMessageConsumer::class)
@AutoConfigureAfter(MessageConsumerAutoConfiguration::class)
open class JmsAutoConfiguration {

    @Bean
    open fun jacksonMessageConverter(): MappingJackson2MessageConverter {
        val mapper = jacksonObjectMapper()

        val converter = MappingJackson2MessageConverter()
        converter.setObjectMapper(mapper)
        converter.setTargetType(MessageType.TEXT)
        converter.setTypeIdPropertyName("type")
        return converter
    }

//    override fun configureJmsListeners(registrar: JmsListenerEndpointRegistrar) {
//        val endpoint = SimpleJmsListenerEndpoint()
//        endpoint.id = "myJmsEndpoint"
//        endpoint.destination = "anotherQueue"
//        endpoint.messageListener = MessageListener {
//            jacksonMessageConverter().fromMessage(it)
//        }
//        registrar.registerEndpoint(endpoint)
//    }
}
