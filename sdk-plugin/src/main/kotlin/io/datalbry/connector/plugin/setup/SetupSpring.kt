package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.ConnectorPluginExtension
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

/**
 * Setup Spring Boot plugin, and preconfigure necessary attributes of the Manifest
 *
 * @author timo gruen - 2021-06-11
 */
fun Project.setupSpringBoot(extension: ConnectorPluginExtension) {
    plugins.apply(SpringBootPlugin::class.java)
    plugins.apply(DependencyManagementPlugin::class.java)

    with (tasks.getByName("bootJar")) {
        this as BootJar
        mainClassName = "io.lbrary.es.connector.sdk.ConnectorApplicationKt"
        manifest {
            it.attributes(
                mapOf(
                    "Connector-Title" to extension.name,
                    "Connector-Group" to extension.group,
                    "Connector-Version" to extension.version
                )
            )
        }
    }
}
