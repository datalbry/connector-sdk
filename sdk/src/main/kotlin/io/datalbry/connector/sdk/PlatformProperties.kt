package io.datalbry.connector.sdk

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
@ConfigurationProperties("io.datalbry.platform")
data class PlatformProperties(
    val uri: URI
)
