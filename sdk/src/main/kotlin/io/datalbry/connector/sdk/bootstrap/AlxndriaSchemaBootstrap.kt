package io.datalbry.connector.sdk.bootstrap

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.schema.SchemaProvider
import org.springframework.stereotype.Component

/**
 * The [AlxndriaSchemaBootstrap] bootstraps alxndria with a schema. Upon start up the  loaded schema will be put into
 * axlndria.
 *
 * @param alxndria: The alxndria client
 * @param schemaProvider: A schema provider.
 * @param properties: Connector properties which contain information about alxndria's datasource.
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
