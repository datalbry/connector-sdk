package io.datalbry.connector.sdk.schema.static

import com.fasterxml.jackson.databind.ObjectMapper
import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import org.springframework.stereotype.Component
import java.io.File

/**
 * This [SchemaProvider] looks for the by Precise generated schema.json inside the JAR at /META-INF/datalbry/schema.json
 */
@Component
class StaticSchemaProvider : SchemaProvider {

    /**
     * Returns a [Schema] loaded from /META-INF/datalbry/schema.json
     *
     * @throws NoSuchFileException if the file is not present
     * @return A [Schema] instance
     */
    override fun getSchema(): Schema {

        val resource = this::class.java.getResource(schemaPath)
            ?: throw java.nio.file.NoSuchFileException("Schema JSON at $schemaPath is not present")

        val objectMapper = ObjectMapper()

        return objectMapper.readValue(File(resource.file), Schema::class.java)
    }

    companion object {
        const val schemaPath = "/META-INF/datalbry/schema.json"
    }
}
