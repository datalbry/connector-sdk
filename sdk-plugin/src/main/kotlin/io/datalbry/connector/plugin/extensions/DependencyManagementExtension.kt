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
 * @author timo gruen - 2021-06-11
 */
@Suppress("LeakingThis")
abstract class DependencyManagementExtension {
    abstract var enabled: Property<Boolean>
    abstract var versionCommonsConfig: Property<String>
    abstract var versionConnectorSdk: Property<String>
    abstract var versionPrecise: Property<String>

    init {
        enabled.convention(true)
        versionCommonsConfig.convention("")
        versionConnectorSdk.convention("")
        versionPrecise.convention("")
    }
}
