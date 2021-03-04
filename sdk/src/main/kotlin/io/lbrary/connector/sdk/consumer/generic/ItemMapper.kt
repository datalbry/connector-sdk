package io.lbrary.connector.sdk.consumer.generic

import io.lbrary.connector.api.DocumentEdge
import io.lbrary.service.index.api.document.Document

/**
 * [ItemMapper]
 */
interface ItemMapper<T> {

    fun getDocuments(item: T): Collection<Document>

    fun getEdges(item: T): Collection<DocumentEdge>

    fun supports(item: T): Boolean

}
