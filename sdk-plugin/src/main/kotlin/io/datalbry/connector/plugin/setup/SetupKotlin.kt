package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.ConnectorPluginExtension
import io.datalbry.connector.plugin.extensions.KotlinExtension
import io.datalbry.connector.plugin.extensions.ProgrammingLanguage
import io.datalbry.connector.plugin.extensions.ProgrammingLanguage.KOTLIN
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Setup language specifics
 *
 * Necessary since we are heavily relying on annotation processing to derive information
 * from our connector implementations.
 *
 * @author timo gruen - 2021-06-11
 */
fun Project.setupLanguage(extension: ConnectorPluginExtension) {
    // FIXME: Check how to pass the arguments, without building a complex,
    //        implicit dependency between the different configurations
    val kotlin = extension.kotlin
    setupJvm(kotlin)
    when (ProgrammingLanguage.byName(extension.language)) {
        KOTLIN -> setupKotlin(kotlin)
    }
}

private fun Project.setupJvm(kotlin: KotlinExtension) {
    project.setupJavaPlugin()
    project.configureJavaCompile(kotlin)
    project.configureJavaPlugin()
}

private fun Project.setupKotlin(kotlin: KotlinExtension) {
    project.dependencies.setupKotlinDependencies(kotlin.version)
    project.configureKotlinCompile(kotlin)
}

private fun Project.setupJavaPlugin() {
    if (!project.plugins.hasPlugin("java")) {
        project.plugins.apply("java")
    }
}

private fun DependencyHandler.setupKotlinDependencies(version: String) {
    add(CONFIGURATION_RUNTIME, "org.jetbrains.kotlin:kotlin-reflect:$version")
    add(CONFIGURATION_IMPLEMENTATION, "org.jetbrains.kotlin:kotlin-stdlib:$version")
}

private fun Project.configureKotlinCompile(kotlin: KotlinExtension) {
    tasks.withType(KotlinCompile::class.java) {
        with(it.kotlinOptions) {
            this.freeCompilerArgs = listOf("-Xjsr305=strict")
            this.jvmTarget = kotlin.targetCompatibility
        }
    }
}

private fun Project.configureJavaPlugin() {
    with(project.extensions.getByType(JavaPluginExtension::class.java)) {
        withJavadocJar()
        withSourcesJar()
    }
}

private fun Project.configureJavaCompile(kotlin: KotlinExtension) {
    tasks.withType(JavaCompile::class.java) {
        it.sourceCompatibility = kotlin.sourceCompatibility
        it.targetCompatibility = kotlin.targetCompatibility
    }
}


