package io.datalbry.connector.sdk.test

import io.datalbry.precise.api.schema.document.Document
import java.time.Duration
import java.util.concurrent.TimeoutException

/**
 * A utility function for testing an asynchronous event where you block until a certain condition is met. This function
 * executes a given function, checks whenever some condition is met or not. If not this will be repeated after some
 * duration until the timeout duration has been reached.
 *
 * @param await A function that takes no arguments but produces some kind of output T which you want to await for.
 * @param until A predicate that checks against an argument of type T whenever to continue awaiting or not.
 * @param assert An assertion on the output of type T which.
 * @param timeout The maximum duration to wait for.
 * @param ignore A set of throwable that will be ignored.
 * @param steps The duration to wait for during each loop.
 *
 * @throws TimeoutException if the timeout has been reached and we did not met the exit condition.
 */
fun <T> await(
    await: () -> T,
    until: (T) -> Boolean = { true },
    assert: (T) -> Unit,
    timeout: Duration,
    ignore: Set<Throwable> = emptySet(),
    steps: Duration = timeout.dividedBy(20)
) {
    val start = System.currentTimeMillis()
    do {
        try {
            if (timedOut(start, timeout)) {
                throw TimeoutException()
            }
            val result = await()
            if (until(result)) {
                assert(result)
                return
            }
            Thread.sleep(steps.toMillis())
        } catch (e: Throwable) {
            if (ignore.map { it::class }.none { e::class == it }) {
                throw e
            }
            Thread.sleep(steps.toMillis())
        }
    } while (true)
}

/**
 * A utility function for testing an asynchronous event where you block until a convergence condition is met. This
 * function executes a given function, checks whenever given the current and the last result of the asynchronous event
 * they meet the condition. If not this will be repeated after some duration until the timeout duration has been
 * reached.
 *
 * @param await A function that takes no arguments but produces some kind of output T which you want to await for.
 * @param convergenceCriterion A predicate that compared the previous result from the await and the current await and exits if we reached convergence.
 * @param timeout The maximum duration to wait for.
 * @param ignore A set of throwable that will be ignored.
 * @param steps The duration to wait for during each loop.
 *
 * @throws TimeoutException if the timeout has been reached and we did not met the exit condition.
 */
fun <T> awaitConvergence(
    await: () -> T,
    convergenceCriterion: (T, T) -> Boolean,
    timeout: Duration,
    steps: Duration = timeout.dividedBy(20),
    ignore: Set<Throwable> = emptySet(),
) {
    val start = System.currentTimeMillis()
    var t0 = await()
    Thread.sleep(steps.toMillis())
    var t1 = await()

    while (convergenceCriterion(t0, t1)) {
        try {
            if (timedOut(start, timeout)) {
                throw TimeoutException()
            }

            Thread.sleep(steps.toMillis())
            t0 = t1
            t1 = await()
        } catch (e: Throwable) {
            if (ignore.map { it::class }.none { e::class == it }) {
                throw e
            }
            Thread.sleep(steps.toMillis())
        }
    }
}


private fun timedOut(startMillis: Long, duration: Duration): Boolean {
    return System.currentTimeMillis() - startMillis >= duration.toMillis()
}



