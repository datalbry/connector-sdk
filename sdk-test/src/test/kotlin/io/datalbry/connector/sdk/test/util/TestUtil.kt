package io.datalbry.connector.sdk.test.util

import java.time.Duration
import java.util.concurrent.TimeoutException


internal fun <T> await(
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

private fun timedOut(startMillis: Long, duration: Duration): Boolean {
    return System.currentTimeMillis() - startMillis >= duration.toMillis()
}
