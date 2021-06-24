package io.datalbry.connector.plugin.util

import org.gradle.api.Plugin
import org.gradle.api.Project

inline fun <reified PluginType: Plugin<*>> Project.enablePlugin() {
    if (!project.plugins.hasPlugin(PluginType::class.java)) {
        project.plugins.apply(PluginType::class.java)
    }
}

fun Project.enablePlugin(id: String) {
    if (!project.plugins.hasPlugin(id)) {
        project.plugins.apply(id)
    }
}

inline fun <reified Type> Project.propertyOrNull(key: String): Type? {
    return if (project.properties.containsKey(key)) property(key) as Type else null
}

inline fun <reified Type> Project.propertyOrDefault(key: String, default: Type): Type {
    return propertyOrNull(key) ?: default
}
