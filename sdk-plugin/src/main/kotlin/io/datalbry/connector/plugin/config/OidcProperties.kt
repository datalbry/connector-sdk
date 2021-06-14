package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

interface OidcProperties {
    val baseUrl: Property<String>
    val realm: Property<String>
    val clientId: Property<String>
    val clientSecret: Property<String>
    val username: Property<String>
    val password: Property<String>
}
