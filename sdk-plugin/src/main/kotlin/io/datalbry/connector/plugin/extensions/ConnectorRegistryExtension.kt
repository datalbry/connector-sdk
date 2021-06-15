package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

@Suppress("LeakingThis")
abstract class ConnectorRegistryExtension {
    abstract var baseUrl: Property<String>
    abstract var snapshotReleaseEnabled: Property<Boolean>

    init {
        baseUrl.convention("")
        snapshotReleaseEnabled.convention(true)
    }
}
