package io.lbrary.connector.sdk.state.jpa.util

import io.lbrary.connector.sdk.state.NodeReference
import io.lbrary.service.index.api.document.DocumentId
import io.lbrary.service.index.api.schema.DocumentSchemaId
import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

/**
 * Creates multiple nodes, using the index as key for the universal unique identifier.
 *
 * @param start index
 * @param end index
 *
 * @return collection of deterministic NodeReferences
 */
internal fun createNodes(
    start: Int,
    end: Int
): Collection<NodeReference> {
    return IntStream
        .range(start, end)
        .mapToObj { UUID.nameUUIDFromBytes("test_$it".toByteArray()) }
        .map { createNode(it) }
        .toList()
}

/**
 * Creates a single node
 *
 * @param uuid of the node, default is a randomUUID
 *
 * @return newly created [NodeReference]
 */
internal fun createNode(
    uuid: UUID = UUID.randomUUID()
): NodeReference {
    return NodeReference(uuid)
}

/**
 * Creates multiple document ids, using the index as key for the universal unique identifier
 *
 * @param start index
 * @param end index
 * @param schemaId for the [DocumentId], defaults to a static one
 *
 * @return collection of deterministic DocumentIds
 */
internal fun createDocumentIds(
    start: Int,
    end: Int,
    schemaId: DocumentSchemaId = DocumentSchemaId("test")
) : Collection<DocumentId> {
    return IntStream
        .range(start, end)
        .mapToObj { UUID.nameUUIDFromBytes("doc_$it".toByteArray())}
        .map { DocumentId(it.toString(), schemaId) }
        .toList()
}
