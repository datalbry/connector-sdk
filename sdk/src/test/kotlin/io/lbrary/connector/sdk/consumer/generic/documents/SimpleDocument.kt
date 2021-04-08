package io.lbrary.connector.sdk.consumer.generic.documents

import io.lbrary.connector.api.annotation.property.Checksum
import io.lbrary.connector.api.annotation.property.Id
import io.lbrary.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
class SimpleDocument(
    @Id val docId: String,
    val title: String,
    val author: String,
    @Checksum val modified: ZonedDateTime
)
