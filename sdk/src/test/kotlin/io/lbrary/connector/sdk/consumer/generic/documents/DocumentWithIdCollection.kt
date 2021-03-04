package io.lbrary.connector.sdk.consumer.generic.documents

import io.lbrary.connector.api.annotation.property.Checksum
import io.lbrary.connector.api.annotation.property.Id
import io.lbrary.connector.api.annotation.property.Property
import io.lbrary.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
class DocumentWithIdCollection(
    @Property @Id val docId: Collection<String>,
    @Property val title: String,
    @Property val contributors: Collection<String>,
    @Property @Checksum val modified: ZonedDateTime
)
