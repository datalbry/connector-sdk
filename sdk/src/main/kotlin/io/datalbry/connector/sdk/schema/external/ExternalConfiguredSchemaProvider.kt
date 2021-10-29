package io.datalbry.connector.sdk.schema.external

import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

/**
 * TODO
 */
class ExternalConfiguredSchemaProvider(): SchemaProvider {

    /**
     * TODO
     */
    override fun getSchema(): Schema {
        TODO("Not yet implemented")
    }
}