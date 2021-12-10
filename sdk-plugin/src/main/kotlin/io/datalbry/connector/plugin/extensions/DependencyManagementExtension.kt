package io.datalbry.connector.plugin.extensions

import io.datalbry.connector.plugin.util.propertyOrDefault
import org.gradle.api.Project
import java.util.*

/**
 * Extension properties for the dependency management of the connector.
 *
 * Refers to the section:
 * """
 * connector {
 *     ...
 *     dependencyManagement {
 *         enabled = true
 *         version = "0.0.5"
 *     }
 * }
 * """
 *
 * @author timo gruen - 2021-06-11
 */
class DependencyManagementExtension(project: Project) {
    var enabled: Boolean = project.propertyOrDefault("connector.dependencies.enabled", true)
    var versionCommonsConfig: String = project.propertyOrDefault("connector.dependencies.versionCommonsConfig", "0.0.2")
    var versionConnectorSdk: String = project.propertyOrDefault("connector.dependencies.versionConnectorSdk", getDefaultConnectorSdkVersion())
    var versionPrecise: String = project.propertyOrDefault("connector.dependencies.versionPrecise", "0.0.10")
}

private fun getDefaultConnectorSdkVersion(): String {
    DependencyManagementExtension::class.java.getResourceAsStream("/version.properties").use {
        val props = Properties()
        props.load(it)
        return props["version"] as String
    }
}
