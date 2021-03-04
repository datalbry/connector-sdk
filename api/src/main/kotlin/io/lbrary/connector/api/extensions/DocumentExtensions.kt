package io.lbrary.connector.api.extensions

import io.lbrary.service.index.api.document.Document
import io.lbrary.service.index.api.schema.Property

/**
 * Extension function for easier use of [Document]s.
 * Internally just wrapping the [Document.metadata] collection.
 *
 * @param key of the metadata you want to access
 *
 * @return [Property]
 *
 * @author timo gruen - 2020-11-12
 */
operator fun Document.get(key: String): Property<*> {
    return this.metadata.first { it.key.key == key }
}
