package io.datalbry.connector.sdk

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConstructorBinding
@ConfigurationProperties("io.datalbry.connector")
data class ConnectorProperties(
    val concurrency: Int,
    val alxndria: AlxndriaProperties
) {

    data class AlxndriaProperties(
        val uri: URI,
        val datasource: String
    )

    companion object {
        const val ALXNDRIA_URI_PROPERTY = "io.datalbry.connector.alxndria.uri"
        const val ALXNDRIA_DATASOURCE_PROPERTY = "io.datalbry.connector.alxndria.datasource"
        const val CONCURRENCY_PROPERTY = "io.datalbry.connector.concurrency"
    }
}
