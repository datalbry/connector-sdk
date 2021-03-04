package io.lbrary.connector.api.root

import io.lbrary.connector.api.DocumentEdge
import java.util.*

/**
 * The [DocumentRoot] marker is used to indicate that a crawl should start.
 *
 * Any [io.lbrary.connector.api.CrawlProcessor] which is able to process the Root,
 * should not rely on any information of the Root, since the Root does not contain any information aside the fact,
 * that a processor should send the first hierarchy of items.
 *
 * @author timo gruen - 2020-11-15
 */
class DocumentRoot

fun createRoot() = DocumentEdge(
    UUID.nameUUIDFromBytes("Root".toByteArray()),
    mapOf(
        "type" to DocumentRoot::class.qualifiedName.toString(),
        //"_class" to DocumentEdge::class.qualifiedName.toString()
    ),
    emptyMap()
)
