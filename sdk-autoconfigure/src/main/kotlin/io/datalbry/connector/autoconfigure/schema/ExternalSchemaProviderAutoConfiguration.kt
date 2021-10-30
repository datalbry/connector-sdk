package io.datalbry.connector.autoconfigure.schema

import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.connector.sdk.schema.external.ExternalSchemaProvider
import io.datalbry.connector.sdk.schema.static.StaticSchemaProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix="io.datalbry.schema", name=arrayOf("external"))
@ConditionalOnMissingBean(SchemaProvider::class)
open class ExternalSchemaProviderAutoConfiguration {

    @Bean
    open fun externalSchemaProvider(): SchemaProvider {
        return ExternalSchemaProvider()
    }

}
