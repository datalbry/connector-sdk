package io.datalbry.connector.autoconfigure.schema

import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.connector.sdk.schema.external.ExternalConfiguredSchemaProvider
import io.datalbry.connector.sdk.schema.external.ExternalConfiguredSchemaProviderProperties
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableConfigurationProperties(ExternalConfiguredSchemaProviderProperties::class)
@ConditionalOnClass(ExternalConfiguredSchemaProviderProperties::class)
@AutoConfigureBefore(SchemaProviderAutoConfiguration::class)
open class ExternalConfiguredSchemaProviderAutoConfiguration {

    @Bean
    @Primary
    open fun externalSchemaProvider(properties: ExternalConfiguredSchemaProviderProperties): SchemaProvider {
        return ExternalConfiguredSchemaProvider(properties)
    }
}
