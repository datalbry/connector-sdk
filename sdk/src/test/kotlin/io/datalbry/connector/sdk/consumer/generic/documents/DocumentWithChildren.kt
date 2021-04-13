package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.api.annotation.property.Children
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Document
import io.datalbry.precise.api.schema.Exclude
import java.time.ZonedDateTime

@Document
class DocumentWithChildren(
    @Id val docId: String,
    val title: String,
    @Checksum val modified: String,
    @Children @Exclude val children: Collection<Child>
)
