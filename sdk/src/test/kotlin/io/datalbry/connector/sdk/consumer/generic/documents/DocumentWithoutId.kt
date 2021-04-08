package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
class DocumentWithoutId(
    val title: String,
    val author: String,
    @Checksum val modified: ZonedDateTime
)
