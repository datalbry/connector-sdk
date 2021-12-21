package io.datalbry.connector.sdk.state.jpa.util

import io.datalbry.connector.sdk.state.DocumentState
import io.datalbry.connector.sdk.state.NodeReference
import java.util.UUID


/**
 * Creates multiple nodes, using the index as key for the universal unique identifier.
 *
 * @param start index
 * @param end index
 *
 * @return collection of deterministic NodeReferences
 */
internal fun createNodes(start: Int, end: Int): Collection<NodeReference> {
    return IntRange(start, end)
        .map { UUID.nameUUIDFromBytes("test_$it".toByteArray()) }
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
internal fun createNode(uuid: UUID = UUID.randomUUID()): NodeReference {
    return NodeReference(uuid)
}

/**
 * Creates multiple document ids, using the index as key for the universal unique identifier
 *
 * @param start index
 * @param end index
 *
 * @return collection of deterministic DocumentIds
 */
internal fun createDocumentStates(start: Int, end: Int) : Collection<DocumentState> {
    return IntRange(start, end)
        .map { UUID.nameUUIDFromBytes("doc_$it".toByteArray()) to it}
        .map { DocumentState(it.first.toString(), it.second.toString()) }
        .toList()
}
