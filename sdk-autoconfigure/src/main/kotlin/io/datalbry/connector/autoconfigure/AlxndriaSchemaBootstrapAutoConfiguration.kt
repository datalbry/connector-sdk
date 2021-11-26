package io.datalbry.connector.autoconfigure

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.bootstrap.AlxndriaSchemaBootstrap
import io.datalbry.connector.sdk.schema.SchemaProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(PlatformClient::class, SchemaProvider::class, ConnectorProperties::class)
open class AlxndriaSchemaBootstrapAutoConfiguration {

    @Bean
    open fun alxndriaSchemaBootstrap(
        alxndria: PlatformClient,
        schemaProvider: SchemaProvider,
        properties: ConnectorProperties
    ): AlxndriaSchemaBootstrap {
        return AlxndriaSchemaBootstrap(alxndria, schemaProvider, properties)
    }
}
