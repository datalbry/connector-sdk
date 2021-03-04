package io.lbrary.connector.api.annotation.stereotype

/**
 * Stereotype of the Connector SDK.
 *
 * [Container]s are objects which do only contain references for other objects.
 * These then have to be consumed up by another [io.lbrary.connector.api.CrawlProcessor].
 *
 * While [Document]s can still contain [io.lbrary.connector.api.annotation.property.Children]s and
 * [io.lbrary.connector.api.annotation.property.Property]s. A [Container] may only have
 * [io.lbrary.connector.api.annotation.property.Children]s.
 *
 * Use this annotation to indicate that a Pojo is a [Container] and may not contain any property information.
 * [Container] are not indexed at all, but being used to determine the hierarchy of a source system.
 *
 * The Pojo can be produced by a [io.lbrary.connector.api.CrawlProcessor].
 *
 * @author timo gruen - 2020-11-12
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Container
