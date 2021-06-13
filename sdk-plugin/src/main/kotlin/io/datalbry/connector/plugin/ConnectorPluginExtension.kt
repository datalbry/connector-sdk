package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.config.*
import org.gradle.api.Project
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
class ConnectorPluginExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    var name: String = TODO()
    var group: String = TODO()
    var version: String = TODO()
    var configSchemaPath: String = TODO()
    var documentSchemaPath: String = TODO()
    var oidc: OidcProperties = TODO()
    var language: ProgrammingLanguage = TODO()
    var container: ContainerProperties = TODO()
    val registry: ConnectorRegistryProperties = TODO()
    val kotlin: KotlinProperties = TODO()
    var dependencyManagement: DependencyManagementProperties = TODO()

}
