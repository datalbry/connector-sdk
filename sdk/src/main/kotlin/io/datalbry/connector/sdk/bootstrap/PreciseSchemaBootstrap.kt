package io.datalbry.connector.sdk.bootstrap

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.connector.sdk.ConnectorProperties
import io.datalbry.connector.sdk.schema.SchemaProvider

/**
 * The [PreciseSchemaBootstrap] bootstraps alxndria with a schema. Upon start up the  loaded schema will be put into
 * axlndria.
 *
 * @param alxndria: The alxndria client
 * @param schemaProvider: A schema provider.
 * @param properties: Connector properties which contain information about alxndria's datasource.
 */
open class PreciseSchemaBootstrap(
    alxndria: PlatformClient,
    schemaProvider: SchemaProvider,
    properties: ConnectorProperties
) {
    init {
        val schema = schemaProvider.getSchema()
        alxndria.datasource.putSchema(properties.alxndria.datasource, schema)
    }
}
