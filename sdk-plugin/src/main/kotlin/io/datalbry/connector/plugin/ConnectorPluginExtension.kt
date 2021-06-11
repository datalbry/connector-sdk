package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.config.DependencyManagementProperties
import io.datalbry.connector.plugin.config.KeycloakProperties
import io.datalbry.connector.plugin.util.domainObjectContainer
import io.datalbry.connector.plugin.util.property
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
class ConnectorPluginExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    var keycloak: NamedDomainObjectContainer<KeycloakProperties> = objects.domainObjectContainer()
    var dependencyManagement: NamedDomainObjectContainer<DependencyManagementProperties> = objects.domainObjectContainer()
    var configSchemaPath: Property<String> = objects.property()
    var documentSchemaPath: Property<String> = objects.property()
}
