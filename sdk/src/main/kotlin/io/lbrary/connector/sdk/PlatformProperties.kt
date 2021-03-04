package io.lbrary.connector.sdk

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
@ConfigurationProperties("io.lbrary.platform")
data class PlatformProperties(
    val uri: URI
)
