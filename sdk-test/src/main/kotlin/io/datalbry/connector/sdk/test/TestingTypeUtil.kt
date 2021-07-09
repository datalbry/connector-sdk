package io.datalbry.connector.sdk.test

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.generic.GenericField

/**
 * Checks if a document (potentially a generic document) is of a certain type
 */
inline fun <reified Type> Document.isOfType() = Type::class.simpleName == this.type

/**
 * Checks if a document contains all given fields
 */
fun <T> Document.containsFields(fields: Collection<Field<T>>): Boolean {
    return this.fields.containsAll(fields);
}

/**
 * Returns all documents from alxndria given the datasource
 *
 * @param datasourceIdentifier: The place where the data in alxndria is stored
 * @return A list of all stored documents
 */
fun PlatformClient.getAllDocumentsByDatasourceIdentifer(datasourceIdentifier: String): List<Document> {
    val identifier = this.index.getDocumentIds(datasourceIdentifier).asSequence().toList()
    return identifier.map { index.getDocument(datasourceIdentifier, it) }
}



