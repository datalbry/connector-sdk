package io.datalbry.connector.sdk.bootstrap

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.schema.SchemaProvider
import org.springframework.stereotype.Component

/**
 * TODO
 */
@Component
open class AlxndriaSchemaBootstrap(
    alxndria: PlatformClient,
    schemaProvider: SchemaProvider,
    properties: ConnectorProperties
) {
    init {
        val schema = schemaProvider.getSchema()
        alxndria.datasource.putSchema(properties.alxndria.datasource, schema)
    }
}
