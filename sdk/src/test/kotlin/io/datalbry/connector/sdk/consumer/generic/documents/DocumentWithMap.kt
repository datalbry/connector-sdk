package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import io.datalbry.connector.sdk.consumer.generic.RecordMap

@Document
data class DocumentWithMap(
    @Id val docId: String,
    val title: String,
    val values: RecordMap,
    @Checksum val modified: String,
)
