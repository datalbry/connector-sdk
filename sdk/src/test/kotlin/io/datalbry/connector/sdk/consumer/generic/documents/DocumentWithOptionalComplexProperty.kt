package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.stereotype.Document
import io.datalbry.precise.api.schema.SchemaAware
import java.util.*

@Document
@SchemaAware
data class DocumentWithOptionalComplexProperty(
    val id: Int,
    val testRecordWithOptionalRecord: Optional<TestRecordWithOptionalRecord>
)


@SchemaAware
data class TestRecordWithOptionalRecord(val id: Int, val testRecord: Optional<TestRecord>)

@SchemaAware
data class TestRecord(val a: Int)
