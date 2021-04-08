package io.datalbry.connector.api.annotation.stereotype

/**
 * Stereotype of the Connector SDK.
 *
 * [Container]s are objects which do only contain references for other objects.
 * These then have to be consumed up by another [io.datalbry.connector.api.CrawlProcessor].
 *
 * While [Document]s can still contain metadata, the [Container] isn't allowed to.
 *
 * Use this annotation to indicate that a Pojo is a [Container] and may not contain any property information.
 * [Container] are not indexed at all, but being used to determine the hierarchy of a source system.
 *
 * The Pojo can be produced by a [io.datalbry.connector.api.CrawlProcessor].
 *
 * @author timo gruen - 2020-11-12
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Container
