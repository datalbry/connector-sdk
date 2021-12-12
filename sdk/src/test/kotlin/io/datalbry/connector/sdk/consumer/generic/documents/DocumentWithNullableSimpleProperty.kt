package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import io.datalbry.precise.api.schema.Nullable
import io.datalbry.precise.api.schema.SchemaAware
import java.util.*

@Document
@SchemaAware
data class DocumentWithNullableSimpleProperty(
    @Id val id: Int,
    @Nullable val optionalString: String?,
    @Nullable val optionalInt: Int?,
    @Nullable val testRecordWithNullableSimpleProperty: TestRecordWithNullableSimpleProperty?
)


@SchemaAware
data class TestRecordWithNullableSimpleProperty(
    val id: Int,
    @Nullable val optionalString: String?,
    @Nullable val optionalInt: Int?,
)

