package io.datalbry.connector.plugin.setup

import io.datalbry.connector.plugin.ConnectorPluginExtension
import org.gradle.api.Project
import org.gradle.api.tasks.WriteProperties
import org.jetbrains.kotlin.util.removeSuffixIfPresent
import org.jetbrains.kotlin.util.suffixIfNot

fun Project.setupVersionHandler() {
    val projectVersion = project.property("connectorVersion") as String
    version = findVersion(projectVersion)

    tasks.register("writeVersion", WriteProperties::class.java) {
        it.outputFile = project.rootProject.file("gradle.properties")
        it.property("projectVersion", version)
    }

    tasks.register("incrementVersion", WriteProperties::class.java) {
        val currentVersion = projectVersion.removeSuffixIfPresent("-SNAPSHOT").substringAfterLast(".")
        val newVersion = "${projectVersion.substringBeforeLast(".")}.${currentVersion + 1}"
        it.outputFile = project.rootProject.file("gradle.properties")
        it.property("projectVersion", newVersion)
    }
}

fun Project.findVersion(baseVersion: String): String {
    return if (this.hasProperty("snapshot")) {
        return baseVersion.suffixIfNot("-SNAPSHOT")
    } else baseVersion
}
