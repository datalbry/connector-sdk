package io.datalbry.connector.sdk

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
@EnableConfigurationProperties(ConnectorProperties::class)
class ConnectorConfig
