package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.ConnectorPluginExtension
import io.datalbry.connector.plugin.task.RegisterConnectorTask
import org.gradle.api.Project

const val TASK_GROUP = "connector"

/**
 * Setup the Tasks of the [io.datalbry.connector.plugin.ConnectorPlugin],
 * which are all accessible within the namespace of the [TASK_GROUP]
 *
 * @author timo gruen - 2021-06-14
 */
fun Project.setupTasks(extension: ConnectorPluginExtension) {
    val registerConnector = project.tasks.register("registerConnector", RegisterConnectorTask::class.java)
    registerConnector.orNull.let { group = TASK_GROUP }
}
