package io.datalbry.connector.sdk.test

import io.datalbry.alxndria.client.api.PlatformClient
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
<<<<<<< HEAD
import io.datalbry.precise.api.schema.document.Record
=======
import io.datalbry.precise.api.schema.document.generic.GenericField
>>>>>>> 87ba005ef6b2e589dc7dc76a24682089fb439d78

/**
 * Checks if a document (potentially a generic document) is of a certain type
 */
<<<<<<< HEAD
inline fun <reified Type> Record.isOfType() = Type::class.simpleName == this.type
=======
inline fun <reified Type> Document.isOfType() = Type::class.simpleName == this.type
>>>>>>> 87ba005ef6b2e589dc7dc76a24682089fb439d78

/**
 * Checks if a document contains all given fields
 */
<<<<<<< HEAD
fun <T> Record.containsFields(fields: Collection<Field<T>>): Boolean {
=======
fun <T> Document.containsFields(fields: Collection<Field<T>>): Boolean {
>>>>>>> 87ba005ef6b2e589dc7dc76a24682089fb439d78
    return this.fields.containsAll(fields);
}

/**
 * Returns all documents from alxndria given the datasource
 *
 * @param datasourceIdentifier: The place where the data in alxndria is stored
 * @return A list of all stored documents
 */
fun PlatformClient.getAllDocumentsByDatasourceIdentifer(datasourceIdentifier: String): List<Document> {
<<<<<<< HEAD
    val identifier = index.getDocumentIds(datasourceIdentifier).asSequence().toList()
    return identifier.map { index.getDocument(datasourceIdentifier, it) }
}

/**
 * Returns all documents from alxndria given the datasource of a certain type
 *
 * @param datasourceIdentifier: The place where the data in alxndria is stored
 * @return A list of all stored documents
 */
inline fun <reified Type> PlatformClient.getAllDocumentsByDatasourceIdentiferOfType(datasourceIdentifier: String): List<Document> {
    val identifier = index.getDocumentIds(datasourceIdentifier).asSequence().toList()
    return identifier.map { index.getDocument(datasourceIdentifier, it) }.filter{ it.isOfType<Type>()}
}



=======
    val identifier = this.index.getDocumentIds(datasourceIdentifier).asSequence().toList()
    return identifier.map { index.getDocument(datasourceIdentifier, it) }
}

>>>>>>> 87ba005ef6b2e589dc7dc76a24682089fb439d78


