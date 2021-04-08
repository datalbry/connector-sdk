package io.datalbry.connector.api.root

import io.datalbry.connector.api.DocumentEdge
import java.util.*

/**
 * The [DocumentRoot] marker is used to indicate that a crawl should start.
 *
 * Any [io.datalbry.connector.api.CrawlProcessor] which is able to process the Root,
 * should not rely on any information of the Root, since the Root does not contain any information aside the fact,
 * that a processor should send the first hierarchy of items.
 *
 * @author timo gruen - 2020-11-15
 */
class DocumentRoot

/**
 * Creates a static [DocumentEdge]
 *
 * [DocumentEdge] is a kotlin data class and therefore described by its content,
 * which is the primary reason we do not require a static object here, but we can rely on
 * creating objects with static content
 *
 * @return [DocumentEdge] linking to the [DocumentRoot]
 */
fun createRoot() = DocumentEdge(
    UUID.nameUUIDFromBytes("Root".toByteArray()),
    mapOf(
        "type" to DocumentRoot::class.qualifiedName.toString(),
        //"_class" to DocumentEdge::class.qualifiedName.toString()
    ),
    emptyMap()
)
