package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document

@Document
data class DocumentWithComplexProperty(
    @Id val docId: String,
    val title: String,
    val author: Person,
    @Checksum val modified: String
)

data class Person(
    val name: String,
    val mail: String
)

