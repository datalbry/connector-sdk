package io.lbrary.connector.api.annotation.stereotype

/**
 * Base stereotype of the connector sdk.
 *
 * Marks an so it gets processed by the connector-sdk.
 *
 * [io.lbrary.es.connector.api.v1.CrawlProcessor] is able to produce items at runtime.
 * All produced items are analysed and being converted to [io.lbrary.es.platform.api.index.Document].
 *
 * @author timo gruen - 2020-11-11
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Item(
        val indexed: Boolean = false
)
