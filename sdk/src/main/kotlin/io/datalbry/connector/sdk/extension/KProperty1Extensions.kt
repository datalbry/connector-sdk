package io.datalbry.connector.sdk.extension

import kotlin.reflect.KProperty1

/**
 * Checks if a [KProperty1] is annotated with
 */
inline fun <reified A> KProperty1<out Any, *>.annotatedWith() =
    this.annotations.any { a -> A::class.java.isInstance(a) }
