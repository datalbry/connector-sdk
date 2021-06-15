import org.gradle.api.Task
import org.gradle.api.tasks.WriteProperties
import io.datalbry.connector.plugin.ConnectorPluginExtension

plugins {
    id("io.datalbry.connector.sdk")
}

tasks.register<DefaultTask>("assertExtensionRespectsDefaults") {
    doLast {
        if (project.extensions.getByType(ConnectorPluginExtension::class.java).version == "1.8") {
            throw GradleException("Failed")
        }
    }
    outputs.upToDateWhen { false }
}
