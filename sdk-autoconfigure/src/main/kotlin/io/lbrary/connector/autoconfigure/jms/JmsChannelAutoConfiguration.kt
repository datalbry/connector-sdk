package io.lbrary.connector.autoconfigure.jms

import io.lbrary.connector.sdk.messaging.jms.JmsAddChannel
import io.lbrary.connector.sdk.messaging.jms.JmsDeletionChannel
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.core.JmsTemplate

@Configuration
@ConditionalOnClass(JmsAddChannel::class, JmsDeletionChannel::class)
@AutoConfigureAfter(JmsAutoConfiguration::class)
open class JmsChannelAutoConfiguration {

    @Bean
    open fun jmsAddChannel(jmsTemplate: JmsTemplate): JmsAddChannel {
        return JmsAddChannel(jmsTemplate)
    }

    @Bean
    open fun jmsDeletionChannel(jmsTemplate: JmsTemplate): JmsDeletionChannel {
        return JmsDeletionChannel(jmsTemplate)
    }

}
