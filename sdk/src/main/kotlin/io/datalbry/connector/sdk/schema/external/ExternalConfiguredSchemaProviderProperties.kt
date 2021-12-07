package io.datalbry.connector.sdk.schema.external

import io.datalbry.config.api.STRING_KEY
import io.datalbry.config.api.annotation.ConfigSchema
import io.datalbry.config.api.annotation.PropertyDescription
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigSchema
@ConstructorBinding
@ConfigurationProperties(prefix = "io.datalbry.schema.external")
data class ExternalConfiguredSchemaProviderProperties (
    @PropertyDescription(
        key = "io.datalbry.schema.external.path",
        group = "External Precise Schema",
        type = STRING_KEY,
        description = "The URL for the sample API instance",
        label = "Filepath",
        required = true
    )
    val path: String
)
