package io.lbrary.connector.api.annotation.stereotype

/**
 * Stereotype of the Connector SDK.
 *
 * [Document]s are candidates which can be indexed by the connector sdk.
 *
 * [Document] is a specialisation of the [Item] stereotype. It can be substituted with the annotation
 * [Item] plus [Item.indexed] := true.
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Item(indexed = true)
annotation class Document
