package io.datalbry.connector.plugin.task

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.connector.plugin.ConnectorPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * The [RegisterConnectorTask] registers a Connector in a Connector Registry.
 *
 * In contrast to the container registry, which keeps the actual container image of the connector,
 * the Connector Registry itself stores meta information of connectors, such as where the image is located,
 * what the configuration looks like and which data is produced by the connector.
 *
 * @author timo gruen - 2021-06-12
 */
class RegisterConnectorTask: DefaultTask() {

    private val json = jacksonObjectMapper()

    init {
        val compileTask = project.task("compile")
        dependsOn(compileTask)
    }

    @TaskAction
    fun publish() {
        // 1. Create Connector (JSON)
        val connectorJson = buildConnectorJson()
        // 2. Keycloak authenticate (exchange Token)
        // 3. Post Connector (JSON) to Connector Registry (use Token)
    }

    private fun buildConnectorJson(): ObjectNode {
        val extension = project.extensions.getByType(ConnectorPluginExtension::class.java)
        val docSchemaFile = File("${project.buildDir.absolutePath}/${extension.documentSchemaPath}")
        val configSchemaFile = File("${project.buildDir.absolutePath}/${extension.configSchemaPath}")

        val root = json.nodeFactory.objectNode()

        val id = json.nodeFactory.objectNode()
        id.put("name", extension.name)
        id.put("version", extension.version)

        root.set<ObjectNode>("id", id)

        root.set<JsonNode>("docSchema", jacksonObjectMapper().readTree(docSchemaFile))
        root.set<JsonNode>("configSchema", jacksonObjectMapper().readTree(configSchemaFile))

        root.put("image", "${extension.container.repository}/${extension.name}:${extension.version}")

        return root
    }

}
