package io.datalbry.connector.plugin.setup

import com.google.cloud.tools.jib.gradle.JibExtension
import com.google.cloud.tools.jib.gradle.JibPlugin
import io.datalbry.connector.plugin.ConnectorPluginExtension
import io.datalbry.connector.plugin.extensions.ContainerExtension
import io.datalbry.connector.plugin.util.enablePlugin
import org.gradle.api.Project

/**
 * Setup Jib, especially the Registry to push the image to
 *
 * @param container properties
 *
 * @author timo gruen - 2021-06-11
 */
fun Project.setupJib(extension: ConnectorPluginExtension) {
    val container = extension.container
    enablePlugin<JibPlugin>()
    afterEvaluate {
        val jib = project.extensions.getByType(JibExtension::class.java)
        with(jib) {
            container {
                it.ports = listOf("8080")
            }
            to {
                it.image = getImageName(container.repository,"${project.name}:${project.version}")
                container.username.let { user -> it.auth.username = user }
                container.password.let { pw -> it.auth.password = pw }
            }
        }
    }
}

private fun getImageName(repo: String?, image: String): String {
    return repo?.let { "$repo/$image" } ?: image
}
