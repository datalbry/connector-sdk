package io.datalbry.connector.api

/**
 * High-level [CrawlProcessor] interface
 *
 * Is being used to provide different feature.
 * To implement a high-level crawl processor for [io.datalbry.precise.api.schema.document.Document]s,
 * one has to implement a [io.datalbry.connector.api.document.DocumentCrawlProcessor].
 *
 * @see io.datalbry.connector.api.document.DocumentCrawlProcessor
 *
 * @param Edges type to consume
 * @param Produces type to produce
 *
 * @author timo gruen - 2020-11-15
 */
interface CrawlProcessor<Edges : Edge<*>, Produces> {

    /**
     * Processes a value of type [Edges]]
     *
     * @param value to process
     *
     * @returns a [Node] of type [Edges] and [Produces]
     */
    fun process(value: Edges): Node<Edges, Produces>

}

