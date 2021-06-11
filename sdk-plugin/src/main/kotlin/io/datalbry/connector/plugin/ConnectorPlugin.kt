package io.datalbry.connector.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConnectorPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, ConnectorPluginExtension::class.java, project)
    }

    companion object {
        const val EXTENSION_NAME = "connector"
    }
}
