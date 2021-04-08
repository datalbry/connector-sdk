package io.lbrary.connector.sdk.consumer.generic

import io.datalbry.precise.api.schema.document.Document
import io.lbrary.connector.api.DocumentEdge

/**
 * [ItemMapper]
 */
interface ItemMapper<T> {

    fun getDocuments(item: T): Collection<Document>

    fun getEdges(item: T): Collection<DocumentEdge>

    fun supports(item: T): Boolean

}
