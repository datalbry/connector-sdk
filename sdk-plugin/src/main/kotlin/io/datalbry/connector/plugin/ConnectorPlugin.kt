package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.setup.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class ConnectorPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val extension = setupExtensions(project)
        project.setupLanguage(extension)
        project.setupVersionHandler()
        project.setupDependencies(extension)
        project.setupSpringBoot(extension)
        project.setupJib(extension)
        project.setupTasks()
    }

    private fun setupExtensions(project: Project): ConnectorPluginExtension {
        val extension = project.extensions.create(EXTENSION_NAME, ConnectorPluginExtension::class.java, project)
        return extension
    }

    companion object {
        const val EXTENSION_NAME = "connector"
    }
}
