package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import io.datalbry.precise.api.schema.SchemaAware
import java.util.*

@Document
@SchemaAware
data class DocumentWithOptionalSimpleProperty(
    @Id val id: Int,
    val optionalString: Optional<String>,
    val optionalInt: Optional<Int>,
    val testRecordWithOptionalSimpleProperty: TestRecordWithOptionalSimpleProperty
)


@SchemaAware
data class TestRecordWithOptionalSimpleProperty(
    val id: Int,
    val optionalString: Optional<String>,
    val optionalInt: Optional<Int>,
)

