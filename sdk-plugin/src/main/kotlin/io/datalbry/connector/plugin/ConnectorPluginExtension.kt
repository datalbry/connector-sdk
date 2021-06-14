package io.datalbry.connector.plugin

import io.datalbry.connector.plugin.config.*
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import javax.inject.Inject

@Suppress("LeakingThis")
abstract class ConnectorPluginExtension @Inject constructor(project: Project) {

    abstract var name: Property<String>
    abstract var group: Property<String>
    abstract var version: Property<String>
    abstract var language: Property<String>
    abstract var configSchemaPath: Property<String>
    abstract var documentSchemaPath: Property<String>

    init {
        name.convention(project.name)
        group.convention(project.group as String)
        version.convention(project.version as String)
        language.convention("kotlin")
        documentSchemaPath.convention("resources/main/META-INF/datalbry/schema.json")
        configSchemaPath.convention("resources/main/META-INF/datalbry/schema-config.json")
    }

    @Nested abstract fun getOidc(): NamedDomainObjectContainer<OidcProperties>
    @Nested abstract fun getContainer(): NamedDomainObjectContainer<ContainerProperties>
    @Nested abstract fun getRegistry(): NamedDomainObjectContainer<ConnectorRegistryProperties>
    @Nested abstract fun getKotlin(): NamedDomainObjectContainer<KotlinProperties>
    @Nested abstract fun getDependencyManagement(): NamedDomainObjectContainer<DependencyManagementProperties>

}
