package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.setup.setupDependencies
import io.datalbry.connector.plugin.setup.setupLanguage
import io.datalbry.connector.plugin.setup.setupSpringBoot
import org.gradle.api.Plugin
import org.gradle.api.Project

class ConnectorPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, ConnectorPluginExtension::class.java, project)
        project.setupLanguage(extension.java, extension.kotlin, extension.dependencyManagement.language)
        project.setupDependencies(extension.dependencyManagement)
        project.setupSpringBoot(extension.spring)

    }

    companion object {
        const val EXTENSION_NAME = "connector"
    }
}
