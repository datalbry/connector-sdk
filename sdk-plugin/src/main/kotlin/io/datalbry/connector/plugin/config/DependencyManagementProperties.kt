package io.datalbry.connector.plugin.config

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
class DependencyManagementProperties {
    val enabled: Boolean = true
    val version = ""
}
