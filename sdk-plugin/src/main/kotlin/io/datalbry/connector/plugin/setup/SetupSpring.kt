package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.config.SpringProperties
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

/**
 * Setup Spring Boot plugin, and preconfigure necessary attributes of the Manifest
 *
 * @author timo gruen - 2021-06-11
 */
fun Project.setupSpringBoot(spring: SpringProperties) {
    plugins.apply(SpringBootPlugin::class.java)
    plugins.apply(DependencyManagementPlugin::class.java)

    with (tasks.getByName("bootJar")) {
        this as BootJar
        mainClassName = "io.lbrary.es.connector.sdk.ConnectorApplicationKt"
        manifest {
            it.attributes(
                mapOf(
                    "Connector-Title" to project.name,
                    "Connector-Group" to project.group,
                    "Connector-Version" to project.version
                )
            )
        }
    }
}
