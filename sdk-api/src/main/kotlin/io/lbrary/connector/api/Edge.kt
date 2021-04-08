package io.lbrary.connector.api

import java.util.*

/**
 * The [Edge] describes a connection of two different [Node]s.
 *
 * The object contains all information necessary for a [CrawlProcessor] to fetch a new [Node].
 *
 * @author timo gruen - 2020-12-27
 */
interface Edge<T> {
    /**
     * The unique identifier of the [Edge].
     *
     * (!) Must be globally unique!
     */
    val uuid: UUID

    /**
     * Meta information of the [Edge]
     */
    val headers: Map<String, String>

    /**
     * The actual payload which contains the information necessary for the [CrawlProcessor] to fetch a new [Node].
     */
    val payload: T
}

/**
 * [DocumentEdge] is a specialised [Edge] for Document processing.
 *
 * The [payload] is typed as a map of String to String.
 *
 * @author timo gruen - 2020-12-27
 */
data class DocumentEdge(
    override val uuid: UUID,
    override val headers: Map<String, String>,
    override val payload: Map<String, String>
) : Edge<Map<String, String>>
