package io.datalbry.connector.sdk.schema.static

import com.fasterxml.jackson.databind.ObjectMapper
import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import org.springframework.stereotype.Component
import java.io.File

/**
 * TODO
 */
@Component
class StaticSchemaProvider : SchemaProvider {

    /**
     * TODO
     */
    override fun getSchema(): Schema {

        val resource = this::class.java.getResource(schemaPath)
            ?: throw java.nio.file.NoSuchFileException("Schema JSON at $schemaPath is not available")

        val objectMapper = ObjectMapper()

        return objectMapper.readValue(File(resource.file), Schema::class.java)
    }

    companion object {
        const val schemaPath = "/META-INF/datalbry/schema.json"
    }
}
