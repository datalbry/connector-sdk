package io.datalbry.connector.plugin.extensions

import io.datalbry.connector.plugin.util.propertyOrDefault
import org.gradle.api.Project

class ConnectorRegistryExtension(project: Project) {
    var baseUrl: String = project.propertyOrDefault("connector.registry.baseUrl","connectors.datalbry.io")
    var snapshotReleaseEnabled: Boolean = project.propertyOrDefault("connector.registry.snapshotReleaseEnabled",true)
}
