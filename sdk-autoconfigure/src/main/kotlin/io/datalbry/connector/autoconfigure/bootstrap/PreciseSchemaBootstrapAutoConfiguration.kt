package io.datalbry.connector.autoconfigure.bootstrap

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.bootstrap.PreciseSchemaBootstrap
import io.datalbry.connector.sdk.schema.SchemaProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(PlatformClient::class, SchemaProvider::class, ConnectorProperties::class)
open class PreciseSchemaBootstrapAutoConfiguration {

    @Bean
    open fun preciseSchemaBootstrap(
        alxndria: PlatformClient,
        schemaProvider: SchemaProvider,
        properties: ConnectorProperties
    ): PreciseSchemaBootstrap {
        return PreciseSchemaBootstrap(alxndria, schemaProvider, properties)
    }
}
