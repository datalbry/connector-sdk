package io.lbrary.connector.sdk.consumer.generic.documents

import io.lbrary.connector.api.annotation.property.Checksum
import io.lbrary.connector.api.annotation.property.Property
import io.lbrary.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
class DocumentWithoutId(
    @Property val title: String,
    @Property val author: String,
    @Property @Checksum val modified: ZonedDateTime
)
