package io.datalbry.connector.sdk.schema.static

import com.fasterxml.jackson.databind.ObjectMapper
import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import org.springframework.stereotype.Component


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

        val inputStream = getResourceSpringBoot()
            ?: getResourceDebugging()
            ?: throw java.nio.file.NoSuchFileException("Schema JSON at /$schemaPath is not present")

        val objectMapper = ObjectMapper()
        return objectMapper.readValue(inputStream, Schema::class.java)
    }

    private fun getResourceSpringBoot() = this::class.java.classLoader.getResourceAsStream("/$schemaPath")
    private fun getResourceDebugging() = this::class.java.getResourceAsStream("/$schemaPath")

    companion object {
        const val schemaPath = "META-INF/datalbry/schema.json"
    }
}
