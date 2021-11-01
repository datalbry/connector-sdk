package io.datalbry.connector.sdk.schema.external

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.serialization.jackson.PreciseModule
import io.datalbry.precise.serialization.jackson.deserializer.SchemaDeserializer


/**
 * This [SchemaProvider] loads an externally passed schema.
 *
 * TODO
 */
class ExternalSchemaProvider : SchemaProvider {

    /**
     * Returns a [Schema]  TODO
     *
     * @return A [Schema] instance
     */
    override fun getSchema(): Schema {
        TODO("not implementedj")
    }
}
