package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.precise.api.schema.Exclude
import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
class DocumentWithExcludedProperty(
    @Id val docId: String,
    val title: String,
    @Exclude val child: String,
    @Checksum val modified: String
)
