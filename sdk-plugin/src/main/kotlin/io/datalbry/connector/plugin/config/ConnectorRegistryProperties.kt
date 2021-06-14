package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

interface ConnectorRegistryProperties {
    val baseUrl: Property<String>
    val snapshotReleaseEnabled: Property<Boolean>
}
