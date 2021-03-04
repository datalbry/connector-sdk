package io.lbrary.connector.sdk.state

/**
 * [LockException] should be thrown whenever having an issue regarding a [Lock].
 *
 * Such an issue might be:
 * 1. Lock has expired
 * 2. Lock is invalid
 * 3. Already locked
 *
 * @param message of the [LockException]
 *
 * @author timo gruen - 2020-10-26
 */
class LockException(message: String): RuntimeException(message)
