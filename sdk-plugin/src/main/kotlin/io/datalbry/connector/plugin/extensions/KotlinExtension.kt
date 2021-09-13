package io.datalbry.connector.plugin.extensions

import io.datalbry.connector.plugin.util.propertyOrDefault
import org.gradle.api.Project

class KotlinExtension(project: Project) {
    var sourceCompatibility: String = project.propertyOrDefault("connector.kotlin.sourceCompatibility","1.8")
    var targetCompatibility: String = project.propertyOrDefault("connector.kotlin.targetCompatibility", "1.8")
    var version: String = project.propertyOrDefault("connector.kotlin.version", "1.5.30")
}
