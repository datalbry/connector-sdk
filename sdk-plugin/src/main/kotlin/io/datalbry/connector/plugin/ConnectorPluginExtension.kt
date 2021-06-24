package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.extensions.*
import org.gradle.api.Action
import org.gradle.api.Project
import javax.inject.Inject

abstract class ConnectorPluginExtension @Inject constructor(private val project: Project) {

    var name: String? = null
        get() = field ?: project.name

    var group: String? = null
        get() = field ?: project.group as String

    var version: String? = null
        get() = field ?: project.version as String

    var language: String = "kotlin"
    var configSchemaPath: String = "resources/main/META-INF/datalbry/schema.json"
    var documentSchemaPath: String = "resources/main/META-INF/datalbry/schema-config.json"

    var oidc: OidcExtension = OidcExtension(project)
    fun oidc(config: Action<in OidcExtension>) {
        config.execute(oidc)
    }

    var registry: ConnectorRegistryExtension = ConnectorRegistryExtension()
    fun registry(config: Action<in ConnectorRegistryExtension>) {
        config.execute(registry)
    }

    var container: ContainerExtension = ContainerExtension(project)
    fun container(config: Action<in ContainerExtension>) {
        config.execute(container)
    }

    var dependencies: DependencyManagementExtension = DependencyManagementExtension()
    fun dependencies(config: Action<in DependencyManagementExtension>) {
        config.execute(dependencies)
    }

    var kotlin: KotlinExtension = KotlinExtension()
    fun kotlin(config: Action<in KotlinExtension>) {
        config.execute(kotlin)
    }
}
