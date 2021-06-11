package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.config.*
import io.datalbry.connector.plugin.util.domainObjectContainer
import io.datalbry.connector.plugin.util.property
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
class ConnectorPluginExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    var keycloak: KeycloakProperties = TODO()
    var language: ProgrammingLanguage = TODO()
    var container: ContainerProperties = TODO()
    val java: JavaProperties = TODO()
    val kotlin: KotlinProperties = TODO()
    val spring: SpringProperties = TODO()
    var dependencyManagement: DependencyManagementProperties = TODO()
    var configSchemaPath: String = TODO()
    var documentSchemaPath: String = TODO()

}
