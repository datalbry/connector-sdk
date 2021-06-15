package io.datalbry.connector.plugin.extensions

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
class DependencyManagementExtension {
    var enabled: Boolean = true
    var versionCommonsConfig: String = ""
    var versionConnectorSdk: String = ""
    var versionPrecise: String = ""
}
