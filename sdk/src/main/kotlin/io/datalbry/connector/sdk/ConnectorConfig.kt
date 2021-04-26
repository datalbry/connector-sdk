package io.datalbry.connector.sdk

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms

@EnableJms
@Configuration
@EnableConfigurationProperties(ConnectorProperties::class)
class ConnectorConfig
