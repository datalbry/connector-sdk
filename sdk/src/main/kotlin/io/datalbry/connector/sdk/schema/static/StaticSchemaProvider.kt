package io.datalbry.connector.sdk.schema.static

import com.fasterxml.jackson.databind.ObjectMapper
import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import org.aspectj.weaver.tools.cache.SimpleCacheFactory.path
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URISyntaxException
import java.net.URL
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile


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

        val inputStream = this::class.java.classLoader.getResourceAsStream("/$schemaPath")
            ?: throw java.nio.file.NoSuchFileException("Schema JSON at /$schemaPath is not present")

        val objectMapper = ObjectMapper()
        return objectMapper.readValue(inputStream, Schema::class.java)
    }

    companion object {
        const val schemaPath = "META-INF/datalbry/schema.json"
    }
}
