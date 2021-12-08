package io.datalbry.connector.sdk.test

import io.datalbry.connector.sdk.schema.SchemaProvider
import io.datalbry.precise.api.schema.Schema
import org.springframework.boot.test.context.TestComponent

@TestComponent
class TestSchemaProvider: SchemaProvider {
    override fun getSchema(): Schema {
        return Schema(emptySet())
    }
}
