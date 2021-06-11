package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.config.DependencyManagementProperties
import org.gradle.api.Project

private const val CONFIGURATION_RUNTIME = "runtimeOnly"
private const val CONFIGURATION_IMPLEMENTATION = "implementation"
private const val CONFIGURATION_TEST_RUNTIME = "testRuntimeOnly"
private const val CONFIGURATION_TEST_IMPLEMENTATION = "testImplementation"

/**
 * Simple function to setup the dependencies of a connector-project
 */
fun Project.setupDependencies(properties: DependencyManagementProperties) {
    if (!properties.enabled) return
    project.dependencies.add(CONFIGURATION_RUNTIME, "")
    project.dependencies.add(CONFIGURATION_IMPLEMENTATION, "")
    project.dependencies.add(CONFIGURATION_TEST_RUNTIME, "")
    project.dependencies.add(CONFIGURATION_TEST_IMPLEMENTATION, "")
}
