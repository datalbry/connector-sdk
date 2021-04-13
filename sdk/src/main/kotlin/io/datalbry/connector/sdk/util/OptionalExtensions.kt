package io.datalbry.connector.sdk.util

import java.util.*

/**
 * Extension function for [Optional] which runs a function if the value is present
 * Returns the reference to the [Optional] anyways.
 *
 * Helpful if one wants to run a function if the value is present and chaining a call,
 * to run a function if the value is not present.
 *
 * Easy to use, together with [orElseRun].
 *
 * @param consumer function to run if value is present
 *
 * @return the instance of the optional
 *
 * @author timo gruen - 2020-10-25
 */
fun <T> Optional<T>.runIfPresent(consumer: (T) -> Unit): Optional<T> {
    this.ifPresent { consumer(it) }
    return this
}

/**
 * Extension function for [Optional] which runs a function if the value is not present.
 * Returns the reference to the [Optional] anyways.
 *
 * @param consumer function to run if the value is not present
 *
 * @return the instance of the optional
 *
 * @author timo gruen - 2020-10-25
 */
fun <T> Optional<T>.orElseRun(consumer: () -> Unit): Optional<T> {
    if (!this.isPresent) {
        consumer()
    }
    return this
}
