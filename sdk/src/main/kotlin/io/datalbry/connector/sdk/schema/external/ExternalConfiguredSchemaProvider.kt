package io.datalbry.connector.sdk.schema.external

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.connector.sdk.schema.static.StaticSchemaProvider
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.serialization.jackson.PreciseModule
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.io.File

/**
 * TODO
 */

class ExternalConfiguredSchemaProvider(private val properties: ExternalConfiguredSchemaProviderProperties): SchemaProvider {

    override fun getSchema(): Schema {
        val inputStream = File(properties.path).inputStream()

        val objectMapper = jacksonObjectMapper()
            .registerModule(PreciseModule(Schema(emptySet())))

        return objectMapper.readValue(inputStream, Schema::class.java)
    }
}