package io.lbrary.connector.api.annotation.property

/**
 * Indicates that a field should be taken into account for the checksum.
 *
 * The [Checksum] is not necessarily being indexed by the connector.
 *
 * If the field should be indexed as well, please annotate the field with [Property] as well.
 *
 * @see Property
 *
 * @author timo gruen - 2020-12-27
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Checksum
