package io.datalbry.connector.plugin.extensions

import org.gradle.api.Project

class OidcExtension(private val project: Project) {
    var baseUrl: String = "login.datalbry.io"
    var realm: String = "datalbry"
    var clientId: String = "connector-registry"
    var clientSecret: String? = null
        get() = field ?: project.property("connector.oidc.clientSecret") as String
    var username: String? = null
        get() = field ?: project.property("connector.oidc.username") as String
    var password: String? = null
        get() = field ?: project.property("connector.oidc.password") as String
}
