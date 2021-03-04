package io.lbrary.connector.sdk.consumer.generic.documents

import io.lbrary.connector.api.DocumentEdge
import io.lbrary.connector.api.annotation.property.Id
import java.net.URI
import java.util.*

data class Child(
    @Id val key: String,
    val uri: URI
)

internal fun Child.toEdge(): DocumentEdge {
    return DocumentEdge(
        UUID.nameUUIDFromBytes(this.key.toByteArray()),
        mapOf(
            "type" to Child::class.qualifiedName.toString()),
        mapOf(
            Child::uri.name to this.uri.toString(),
            Child::key.name to this.key))
}
