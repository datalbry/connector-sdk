package io.datalbry.connector.plugin.extensions

import io.datalbry.connector.plugin.util.propertyOrDefault
import io.datalbry.connector.plugin.util.propertyOrNull
import org.gradle.api.Project

class ContainerExtension(project: Project) {
    var enabled: Boolean = project.propertyOrDefault("connector.container.enabled", true)
    var repository: String = project.propertyOrDefault("connector.container.repository", "images.datalbry.io")
    var username: String? = project.propertyOrNull("connector.container.username")
    var password: String? = project.propertyOrNull("connector.container.password")
}
