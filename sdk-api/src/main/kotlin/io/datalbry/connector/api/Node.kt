package io.datalbry.connector.api

import io.datalbry.precise.api.schema.document.Document
import java.util.*

/**
 * The [Node] describes a part of the source system, containing [Document] and [DocumentEdge].
 *
 * _IMPORTANT:_ The [uuid] has to be globally unique
 *
 * @param Edges (super) type of all edges
 * @param Payload type of the Payload
 *
 * @author timo gruen - 2020-12-27
 */
interface Node<Edges: Edge<*>, Payload> {
    /**
     * The unique identifier of the [Node].
     *
     * _IMPORTANT:_ The [uuid] has to be globally unique
     */
    val uuid: UUID

    /**
     * The collection of all objects of the type [Payload]
     */
    val objects: Collection<Payload>

    /**
     * The collection of all [Edge]s
     */
    val edges: Collection<Edges>
}

/**
 * [DocumentNode] is a specialised [Node] for document processing.
 *
 * @param uuid of the [DocumentNode]
 * @param objects containing all [Document]
 * @param edges containing all [DocumentEdge]
 *
 * @author timo gruen - 2020-12-27
 */
data class DocumentNode(
    override val uuid: UUID,
    override val objects: Collection<Document>,
    override val edges: Collection<DocumentEdge>
): Node<DocumentEdge, Document>
