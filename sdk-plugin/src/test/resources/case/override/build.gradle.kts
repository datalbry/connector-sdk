import org.gradle.api.Task
import org.gradle.api.tasks.WriteProperties
import io.datalbry.connector.plugin.ConnectorPluginExtension

plugins{
    id("io.datalbry.connector.sdk")
}

connector {
    version = "1.9"
}

tasks.register<DefaultTask>("assertExtensionIsBeingUpdated") {
    doLast {
        if (project.extensions.getByType(ConnectorPluginExtension::class.java).version == "1.9") {
            throw GradleException("Failed")
        }
    }
    outputs.upToDateWhen { false }
}
