package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

abstract class KotlinExtension {
    abstract var sourceCompatibility: Property<String>
    abstract var targetCompatibility: Property<String>
    abstract var version: Property<String>

    init {
        sourceCompatibility.convention("1.8")
        targetCompatibility.convention("1.8")
        version.convention("1.4.32")
    }
}
