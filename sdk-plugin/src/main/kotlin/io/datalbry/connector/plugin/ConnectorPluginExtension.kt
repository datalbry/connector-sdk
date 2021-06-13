package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.config.*
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import javax.inject.Inject

interface ConnectorPluginExtension {
    var name: Property<String>
    var group: Property<String>
    var version: Property<String>
    var language: Property<String>
    var configSchemaPath: Property<String>
    var documentSchemaPath: Property<String>

    @Nested
    fun getOidc(): OidcProperties
    fun oidc(action: Action<in OidcProperties>)

    @Nested
    fun getContainer(): ContainerProperties
    fun container(action: Action<in ContainerProperties>)

    @Nested
    fun getRegistry(): ConnectorRegistryProperties
    fun registry(action: Action<in ConnectorRegistryProperties>)

    @Nested
    fun getKotlin(): KotlinProperties
    fun kotlin(action: Action<in KotlinProperties>)

    @Nested
    fun getDependencyManagement(): DependencyManagementProperties
    fun dependencyManagement(action: Action<in DependencyManagementProperties>)
}
