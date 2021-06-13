package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

interface ContainerProperties {
    val enabled: Property<Boolean>
    val repository: Property<String>
    val username: Property<String>
    val password: Property<String>
}
