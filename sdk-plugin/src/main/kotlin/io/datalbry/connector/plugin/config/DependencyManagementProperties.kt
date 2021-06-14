package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

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
 * @param enabled if true, the Plugin will configure the necessary dependencies,
 *        if false, no dependencies will be configured
 * @param version of the connectorSdk dependencies
 *
 * @author timo gruen - 2021-06-11
 */
interface DependencyManagementProperties {
    val enabled: Property<Boolean>
    val versionCommonsConfig: Property<String>
    val versionConnectorSdk: Property<String>
    val versionPrecise: Property<String>
}
