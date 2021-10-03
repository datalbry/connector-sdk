package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.extensions.*
import io.datalbry.connector.plugin.util.propertyOrDefault
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

    var language: String = project.propertyOrDefault("connector.language","kotlin")
    var configSchemaPath: String = project.propertyOrDefault("connector.configSchemaPath","generated/ksp/main/resources/META-INF/datalbry/schema.json")
    var documentSchemaPath: String = project.propertyOrDefault("connector.documentSchemaPath","generated/ksp/main/resources/META-INF/datalbry/schema-config.json")

    var oidc: OidcExtension = OidcExtension(project)
    fun oidc(config: Action<in OidcExtension>) {
        config.execute(oidc)
    }

    var registry: ConnectorRegistryExtension = ConnectorRegistryExtension(project)
    fun registry(config: Action<in ConnectorRegistryExtension>) {
        config.execute(registry)
    }

    var container: ContainerExtension = ContainerExtension(project)
    fun container(config: Action<in ContainerExtension>) {
        config.execute(container)
    }

    var dependencies: DependencyManagementExtension = DependencyManagementExtension(project)
    fun dependencies(config: Action<in DependencyManagementExtension>) {
        config.execute(dependencies)
    }

    var kotlin: KotlinExtension = KotlinExtension(project)
    fun kotlin(config: Action<in KotlinExtension>) {
        config.execute(kotlin)
    }
}
