package io.datalbry.connector.sdk.state

import java.util.*

/**
 * [Lock] data class which acts as a basic wrapper type for
 * any [Lock]s for the [ConnectorDocumentState]
 *
 * @param uuid of the lock
 *
 * @author timo gruen - 2020-10-26
 */
data class Lock(
        val uuid: UUID
)
