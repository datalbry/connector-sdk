package io.datalbry.connector.plugin.config

import org.gradle.api.provider.Property

@Suppress("LeakingThis")
abstract class OidcExtension {
    abstract var baseUrl: Property<String>
    abstract var realm: Property<String>
    abstract var clientId: Property<String>
    abstract var clientSecret: Property<String>
    abstract var username: Property<String>
    abstract var password: Property<String>

    init {
        baseUrl.convention("login.datalbry.io")
        realm.convention("datalbry")
        clientId.convention("connector-registry")
    }
}
