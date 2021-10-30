package io.datalbry.connector.sdk.schema

import io.datalbry.precise.api.schema.Schema

/**
 * [SchemaProvider] interface
 *
 * Is used to provide schema for a processor.
 */
interface SchemaProvider {

    /**
     * Returns a [Schema] which is used by the [PlatformClient]
     *
     * @see io.datalbry.alxndria.client.api.PlatformClient
     *
     * @return An instance of [Schema]
     */
    fun getSchema(): Schema

}
