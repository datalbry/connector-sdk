package io.datalbry.connector.sdk.consumer.generic

import io.datalbry.connector.api.DocumentEdge
import io.datalbry.precise.api.schema.document.Document

/**
 * The [ItemMapper] creates a Document from a given type T.
 */
interface ItemMapper<T> {

    /**
     * The [getDocuments] method returns documents from the given item.
     *
     * @param item: Object which will be mapped to a document
     * @return A collection of documents
     */
    fun getDocuments(item: T): Collection<Document>

    /**
     * The [getDocuments] method returns the document edges from a given item.
     *
     * @param item: Object which will be mapped to a document
     * @return A collection of document edges
     */
    fun getEdges(item: T): Collection<DocumentEdge>

    /**
     * The [supports] method check if the item is supported. If an item is supported by an item mapper can execute the
     * [getDocuments] and [getEdges] methods on the item.
     *
     * @param item: Object of some type which will be checked if supported
     * @return true if supported, false otherwise.
     */
    fun supports(item: T): Boolean

}
