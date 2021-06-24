package io.datalbry.connector.plugin.extensions

import io.datalbry.connector.plugin.util.propertyOrDefault
import io.datalbry.connector.plugin.util.propertyOrNull
import org.gradle.api.Project

class OidcExtension(private val project: Project) {
    var baseUrl: String = project.propertyOrDefault("connector.oidc.baseUrl", "login.datalbry.io")
    var realm: String = project.propertyOrDefault("connector.oidc.realm", "datalbry")
    var clientId: String = project.propertyOrDefault("connector.oidc.clientId", "connector-registry")
    var clientSecret: String? = project.propertyOrNull("connector.oidc.clientSecret")
    var username: String? = project.propertyOrNull("connector.oidc.username")
    var password: String? = project.propertyOrNull("connector.oidc.password")
}
