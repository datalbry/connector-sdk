package io.datalbry.connector.plugin.extensions

class OidcExtension {
    var baseUrl: String = "login.datalbry.io"
    var realm: String = "datalbry"
    var clientId: String = "connector-registry"
    var clientSecret: String? = null
    var username: String? = null
    var password: String? = null
}
