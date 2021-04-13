package io.datalbry.connector.api.annotation.stereotype

/**
 * Base stereotype of the connector sdk.
 *
 * Marks an so it gets processed by the connector-sdk.
 *
 * [io.datalbry.connector.api.CrawlProcessor] is able to produce items at runtime.
 * All produced items are analysed and being converted to [io.datalbry.precise.api.schema.document.Document]s.
 *
 * @author timo gruen - 2020-11-11
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Item(
        val indexed: Boolean = false
)
