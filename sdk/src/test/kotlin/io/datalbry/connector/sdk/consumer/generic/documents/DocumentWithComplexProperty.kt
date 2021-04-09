package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import java.time.ZonedDateTime

@Document
data class DocumentWithComplexProperty(
    @Id val docId: String,
    val title: String,
    val author: Person,
    @Checksum val modified: ZonedDateTime
)

data class Person(
    val name: String,
    val mail: String
)

