package io.datalbry.connector.plugin.setup

import com.google.devtools.ksp.gradle.KspGradleSubplugin
import io.datalbry.connector.plugin.ConnectorPluginExtension
import io.datalbry.connector.plugin.extensions.DependencyManagementExtension
import io.datalbry.connector.plugin.extensions.ProgrammingLanguage
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal const val CONFIGURATION_COMPILE = "compileOnly"
internal const val CONFIGURATION_RUNTIME = "runtimeOnly"
internal const val CONFIGURATION_IMPLEMENTATION = "implementation"
internal const val CONFIGURATION_TEST_RUNTIME = "testRuntimeOnly"
internal const val CONFIGURATION_TEST_IMPLEMENTATION = "testImplementation"
internal const val CONFIGURATION_KSP = "ksp"

/**
 * Simple function to setup the dependencies of a connector-project
 *
 * @author timo gruen - 2021-06-11
 */
fun Project.setupDependencies(extension: ConnectorPluginExtension) {
    val properties = extension.dependencies
    val language = ProgrammingLanguage.byName(extension.language)
    if (!properties.enabled) return
    val dependencies = project.dependencies
    project.setupRepositories()
    project.setupKsp()
    dependencies.setupConnectorSdkDependencies(properties)
    dependencies.setupPreciseDependencies(language, properties)
    dependencies.setupCommonsConfigDependencies(language, properties)
}

private fun Project.setupRepositories() {
    with(project.repositories) {
        google()
        mavenCentral()
    }
}

private fun Project.setupKsp() {
    project.plugins.apply(KspGradleSubplugin::class.java)
}

private fun DependencyHandler.setupCommonsConfigDependencies(
    language: ProgrammingLanguage,
    extension: DependencyManagementExtension
) {
    val version = extension.versionCommonsConfig
    add(CONFIGURATION_RUNTIME, "io.datalbry.commons:commons-config-api:$version")
    when (language) {
        ProgrammingLanguage.KOTLIN -> add(CONFIGURATION_KSP, "io.datalbry.commons:commons-config-processor-kotlin:$version")
    }
}

private fun DependencyHandler.setupPreciseDependencies(
    language: ProgrammingLanguage,
    extension: DependencyManagementExtension
) {
    val version = extension.versionPrecise
    when (language) {
        ProgrammingLanguage.KOTLIN -> add(CONFIGURATION_KSP, "io.datalbry.precise:precise-processor-kotlin:$version")
    }
}

private fun DependencyHandler.setupConnectorSdkDependencies(extension: DependencyManagementExtension) {
    val version = extension.versionConnectorSdk
    add(CONFIGURATION_RUNTIME, "io.datalbry.connector:connector-sdk:$version")
    add(CONFIGURATION_RUNTIME, "io.datalbry.connector:connector-sdk-autoconfigure:$version")
    add(CONFIGURATION_IMPLEMENTATION, "io.datalbry.connector:connector-sdk-api:$version")
    add(CONFIGURATION_TEST_IMPLEMENTATION, "io.datalbry.connector:connector-sdk-test:$version")
}
