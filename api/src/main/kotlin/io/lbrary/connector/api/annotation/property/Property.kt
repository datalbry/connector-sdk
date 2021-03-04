package io.lbrary.connector.api.annotation.property

/**
 * Indicates that a field should be indexed by the connector.
 *
 * @author timo gruen - 2020-12-27
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Property(val key: String = "", val isOptional: Boolean = false)
