package io.lbrary.connector.api.document

import io.datalbry.precise.api.schema.document.Document
import io.lbrary.connector.api.CrawlProcessor
import io.lbrary.connector.api.DocumentEdge

/**
 * Low-Level [DocumentCrawlProcessor] is the main entry class for the connector-sdk.
 *
 * The [DocumentCrawlProcessor] has to add the persistence layer as well as the message layer.
 *
 * _NOTE:_
 * For the more sophisticated approach please consider using [DocumentProcessor] instead.
 * The [DocumentCrawlProcessor] has a default implementation which uses all [DocumentProcessor] which are being
 * present in the IoC Container of Spring.
 *
 * For more information please see the connector-sdk (implementation).
 *
 * @author timo gruen - 2020-12-27
 */
interface DocumentCrawlProcessor: CrawlProcessor<DocumentEdge, Document>
