package io.datalbry.connector.plugin.extensions

import org.gradle.api.Project

class ContainerExtension(private val project: Project) {
    var enabled: Boolean = true
    var repository: String = "images.datalbry.io"
    var username: String? = null
        get() = field ?: project.property("connector.container.username") as String
    var password: String? = null
        get() = field ?: project.property("connector.container.password") as String
}
