package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

@Suppress("LeakingThis")
abstract class ContainerExtension {
    abstract var enabled: Property<Boolean>
    abstract var repository: Property<String>
    abstract var username: Property<String>
    abstract var password: Property<String>

    init {
        enabled.convention(true)
        repository.convention("")
    }
}
