package io.lbrary.connector.api.annotation.property

/**
 * Indicates that a [Property] contains binary information of a document.
 *
 * _NOTE:_
 * There might be only a single [Content] annotated field per [io.lbrary.connector.api.annotation.stereotype.Document]
 *
 * TODO: currently Content is not supported by the generic SDK
 *
 * @author timo gruen - 2020-12-27
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Content
