package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
class DocumentWithIdCollection(
    @Id val docId: Collection<String>,
    val title: String,
    val contributors: Collection<String>,
    @Checksum val modified: ZonedDateTime
)
