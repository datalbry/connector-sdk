package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

interface KotlinProperties {
    val sourceCompatibility: Property<String>
    val targetCompatibility: Property<String>
    val version: Property<String>
}
