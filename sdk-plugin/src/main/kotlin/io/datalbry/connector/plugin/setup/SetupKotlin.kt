package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.config.JavaProperties
import io.datalbry.connector.plugin.config.KotlinProperties
import io.datalbry.connector.plugin.config.ProgrammingLanguage
import io.datalbry.connector.plugin.config.ProgrammingLanguage.KOTLIN
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
fun Project.setupLanguage(java: JavaProperties, kotlin: KotlinProperties, language: ProgrammingLanguage) {
    // FIXME: Check how to pass the arguments, without building a complex,
    //        implicit dependency between the different configurations
    setupJvm(kotlin)
    when (language) {
        KOTLIN -> setupKotlin(kotlin)
        ProgrammingLanguage.JAVA -> TODO()
    }
}

private fun Project.setupJvm(kotlin: KotlinProperties) {
    project.setupJavaCompile(kotlin)
    project.setupJavaPlugin()
}

private fun Project.setupKotlin(kotlin: KotlinProperties) {
    project.dependencies.setupKotlinDependencies(kotlin.version)
    project.setupKotlinCompile(kotlin)
}

private fun DependencyHandler.setupKotlinDependencies(version: String) {
    add(CONFIGURATION_RUNTIME, "org.jetbrains.kotlin:kotlin-reflect:$version")
    add(CONFIGURATION_IMPLEMENTATION, "org.jetbrains.kotlin:kotlin-stdlib:$version")
}

private fun Project.setupKotlinCompile(kotlin: KotlinProperties) {
    tasks.withType(KotlinCompile::class.java) {
        with(it.kotlinOptions) {
            this.freeCompilerArgs = listOf("-Xjsr305=strict")
            this.jvmTarget = kotlin.targetCompatibility
        }
    }
}

private fun Project.setupJavaPlugin() {
    with(project.extensions.getByType(JavaPluginExtension::class.java)) {
        withJavadocJar()
        withSourcesJar()
    }
}

private fun Project.setupJavaCompile(kotlin: KotlinProperties) {
    tasks.withType(JavaCompile::class.java) {
        it.sourceCompatibility = kotlin.sourceCompatibility
        it.targetCompatibility = kotlin.targetCompatibility
    }
}


