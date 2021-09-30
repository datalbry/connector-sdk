package io.datalbry.connector.plugin.setup

import java.util.Properties
import org.gradle.api.Project
import org.gradle.api.tasks.WriteProperties
import org.jetbrains.kotlin.util.removeSuffixIfPresent
import org.jetbrains.kotlin.util.suffixIfNot

fun Project.setupVersionHandler() {
    val projectVersion = project.version as String
    version = findVersion(projectVersion)

    tasks.register("writeVersion", WriteProperties::class.java) {
        val propertiesFile = project.rootProject.file("gradle.properties")
        it.outputFile = propertiesFile
        val properties = Properties()
        properties.load(propertiesFile.inputStream())
        properties.forEach { property -> it.property(property.key as String, property.value) }
        it.property("version", version)
    }

    tasks.register("incrementVersion", WriteProperties::class.java) {
        val currentVersion = projectVersion.removeSuffixIfPresent("-SNAPSHOT").substringAfterLast(".")
        val newVersion = "${projectVersion.substringBeforeLast(".")}.${currentVersion + 1}"
        val propertiesFile = project.rootProject.file("gradle.properties")
        it.outputFile = propertiesFile
        val properties = Properties()
        properties.load(propertiesFile.inputStream())
        properties.forEach { property -> it.property(property.key as String, property.value) }
        it.property("version", newVersion)
    }
}

fun Project.findVersion(baseVersion: String): String {
    return if (this.hasProperty("snapshot")) {
        return baseVersion.suffixIfNot("-SNAPSHOT")
    } else baseVersion
}
