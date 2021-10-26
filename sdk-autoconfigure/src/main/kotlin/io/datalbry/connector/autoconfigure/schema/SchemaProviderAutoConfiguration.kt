package io.datalbry.connector.autoconfigure.schema

import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.connector.sdk.schema.static.StaticSchemaProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnMissingBean(SchemaProvider::class)
open class SchemaProviderAutoConfiguration {

    @Bean
    open fun staticSchemaProvider(): SchemaProvider {
        return StaticSchemaProvider()
    }

}
