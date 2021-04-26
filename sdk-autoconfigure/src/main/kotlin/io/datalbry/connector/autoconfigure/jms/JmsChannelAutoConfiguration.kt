package io.datalbry.connector.autoconfigure.jms

import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.messaging.jms.JmsAddChannel
import io.datalbry.connector.sdk.messaging.jms.JmsDeletionChannel
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
    open fun jmsAddChannel(
        props: ConnectorProperties,
        jmsTemplate: JmsTemplate
    ): JmsAddChannel {
        return JmsAddChannel(props, jmsTemplate)
    }

    @Bean
    open fun jmsDeletionChannel(
        props: ConnectorProperties,
        jmsTemplate: JmsTemplate
    ): JmsDeletionChannel {
        return JmsDeletionChannel(props, jmsTemplate)
    }

}
