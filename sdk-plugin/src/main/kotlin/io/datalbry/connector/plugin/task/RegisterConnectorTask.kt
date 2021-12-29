package io.datalbry.connector.plugin.task

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.connector.plugin.ConnectorPluginExtension
import io.datalbry.connector.plugin.extensions.ConnectorRegistryExtension
import io.datalbry.connector.plugin.extensions.OidcExtension
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.util.prefixIfNot
import java.io.File
import org.apache.http.client.methods.HttpPut
import org.gradle.api.logging.LogLevel
import org.gradle.internal.impldep.com.google.api.client.http.HttpStatusCodes

/**
 * The [RegisterConnectorTask] registers a Connector in a Connector Registry.
 *
 * In contrast to the container registry, which keeps the actual container image of the connector,
 * the Connector Registry itself stores meta information of connectors, such as where the image is located,
 * what the configuration looks like and which data is produced by the connector.
 *
 * @author timo gruen - 2021-06-12
 */
open class RegisterConnectorTask: DefaultTask() {

    private val json = jacksonObjectMapper()
    private val http = HttpClientBuilder.create().build()

    init {
        val compileTask = project.task("compile")
        dependsOn(compileTask)
    }

    @TaskAction
    fun publish() {
        val connectorJson = buildConnectorJson()
        val accessToken = fetchOidcToken()
        putConnectorToRegistry(connectorJson, accessToken)
    }

    private fun putConnectorToRegistry(connectorJson: String, accessToken: String) {
        val extension = project.extensions.getByType(ConnectorPluginExtension::class.java)
        val registry = extension.registry
        val baseUrl = registry.baseUrl.prefixIfNot("https://")
        var requestUrl = "$baseUrl/connector/registry"

        if (snapshotEnabled(registry) && isSnapshotRelease(extension)) {
            requestUrl += "?namespace=snapshot"
        }

        val put = HttpPut(requestUrl)
        put.addHeader("Content-Type", "application/json")
        put.addHeader("Authorization", "Bearer $accessToken")
        put.entity = StringEntity(connectorJson)

        val response = http.execute(put)
        val statusCode = response.statusLine.statusCode
        if (!validStatusCodes.contains(statusCode)) {
            project.logger.log(LogLevel.ERROR, "StatusCode[$statusCode]: Not able to publish Connector.")
            throw RuntimeException("StatusCode[$statusCode]: Not able to publish Connector.")
        }
    }

    private fun snapshotEnabled(registry: ConnectorRegistryExtension) =
        registry.snapshotReleaseEnabled

    private fun isSnapshotRelease(extension: ConnectorPluginExtension) =
        extension.version!!.endsWith("-SNAPSHOT")

    private fun buildConnectorJson(): String {
        val extension = project.extensions.getByType(ConnectorPluginExtension::class.java)
        val container = extension.container

        val schemaFile = File("${project.buildDir.absolutePath}/${extension.documentSchemaPath}")
        val configFile = File("${project.buildDir.absolutePath}/${extension.configSchemaPath}")

        val root = json.nodeFactory.objectNode()

        val id = json.nodeFactory.objectNode()
        id.put("name", extension.name)
        id.put("version", extension.version)

        root.set<ObjectNode>("id", id)
        if(schemaFile.exists()) {
            root.set<JsonNode>("schema", jacksonObjectMapper().readTree(schemaFile))
        }
        root.set<JsonNode>("config", jacksonObjectMapper().readTree(configFile))
        root.put("image", "${container.repository}/${extension.name}:${extension.version}")

        return json.writeValueAsString(root)
    }

    private fun fetchOidcToken(): String {
        val extension = project.extensions.getByType(ConnectorPluginExtension::class.java)
        val oidc = extension.oidc
        val baseUrl = oidc.baseUrl.prefixIfNot("https://")
        val requestUrl = "$baseUrl/auth/realms/${oidc.realm}/protocol/openid-connect/token"

        val post = HttpPost(requestUrl)
        post.addHeader("Content-Type", "application/x-www-form-urlencoded")
        post.entity = oidc.createFormEntity()

        val response = http.execute(post)
        val jsonResponse = EntityUtils.toString(response.entity)
        return json.readTree(jsonResponse).get("access_token").asText()
    }

    private fun OidcExtension.createFormEntity(): UrlEncodedFormEntity {
        val formValues = listOf(
            BasicNameValuePair("username", username!!),
            BasicNameValuePair("password", password!!),
            BasicNameValuePair("grant_type", "password"),
            BasicNameValuePair("client_id", clientId),
            BasicNameValuePair("client_secret", clientSecret!!)
        )

        return UrlEncodedFormEntity(formValues, "UTF-8")
    }

    companion object {
        private val validStatusCodes = listOf(
            HttpStatusCodes.STATUS_CODE_OK,
            HttpStatusCodes.STATUS_CODE_ACCEPTED,
            HttpStatusCodes.STATUS_CODE_CREATED
        )
    }
}
