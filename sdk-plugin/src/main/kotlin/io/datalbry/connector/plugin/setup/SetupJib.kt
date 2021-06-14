package io.datalbry.connector.plugin.setup

import com.google.cloud.tools.jib.gradle.JibExtension
import io.datalbry.connector.plugin.config.ContainerProperties
import org.gradle.api.Project

/**
 * Setup Jib, especially the Registry to push the image to
 *
 * @param container properties
 *
 * @author timo gruen - 2021-06-11
 */
fun Project.setupJib(container: ContainerProperties) {
    afterEvaluate {
        val jib = project.extensions.getByType(JibExtension::class.java)
        with(jib) {
            container {
                it.ports = listOf("8080")
            }
            to {
                it.image = getImageName(container.repository.getOrElse("images.datalbry.io"),"${project.name}:${project.version}")
                container.username.orNull.let { user -> it.auth.username = user }
                container.password.orNull.let { pw -> it.auth.password = pw }
            }
        }
    }
}

private fun getImageName(repo: String?, image: String): String {
    return repo?.let { "$repo/$image" } ?: image
}
