package io.lbrary.connector.sdk.consumer.generic

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.lbrary.connector.api.DocumentEdge
import io.lbrary.connector.sdk.consumer.generic.GenericCrawlProcessor.Companion.TYPE_KEY
import io.lbrary.service.index.api.document.Document
import io.lbrary.service.index.api.document.DocumentId
import io.lbrary.service.index.api.extensions.getStringRepresentation
import io.lbrary.service.index.api.schema.DocumentSchemaId
import io.lbrary.service.index.api.schema.Property
import io.lbrary.service.index.api.schema.PropertyKey
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.cast
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter
import io.lbrary.connector.api.annotation.property.Children as ChildrenAnnotation
import io.lbrary.connector.api.annotation.property.Id as IdAnnotation
import io.lbrary.connector.api.annotation.property.Property as PropertyAnnotation
import io.lbrary.connector.api.annotation.stereotype.Document as DocumentAnnotation

/**
 * The GenericItemMapper is able to map a candidate of type `Any` to a collection of documents
 * as well as to a collection of edges.
 *
 * Proceed with caution. The class is highly complicated and is using reflection excessively.
 *
 * @author timo gruen - 2020-11-15
 */
class GenericItemMapper(
        private val clazz: KClass<*>
)
    : ItemMapper<Any>
{
    private val getter = clazz.declaredMemberProperties
    private val jackson = jacksonObjectMapper()

    override fun getDocuments(item: Any): Collection<Document> {
        assert(supports(item)) { "[$this] does not support [${item.javaClass}]." }

        if (clazz.annotations.none(DocumentAnnotation::class::isInstance)) return emptyList()

        val identifier = getDocumentIdentifier(item)
        val metadata = getMetadata(item)

        val document = Document(identifier, metadata)

        return listOf(document)
    }

    override fun getEdges(item: Any): Collection<DocumentEdge> {
        return getter
                .asSequence()
                .filter { it.annotations.any(ChildrenAnnotation::class::isInstance) }
                .map { it.javaGetter!!.invoke(item) }
                .map { toCollection(it) }
                .flatten()
                .filterNotNull()
                .map { it to getEdgeIdentifier(it) }
                .map { DocumentEdge(it.second, mapOf(TYPE_KEY to it.first::class.java.name), jackson.convertValue(it.first)) }
                .toList()
    }

    override fun supports(item: Any): Boolean {
        return clazz.isInstance(item)
    }

    private fun getDocumentTypeIdentifier(): DocumentSchemaId {
        val annotation = clazz.annotations.first(DocumentAnnotation::class::isInstance)
        annotation as DocumentAnnotation

        val name = if (annotation.name.isBlank()) clazz.simpleName.toString() else annotation.name
        return DocumentSchemaId(name)
    }

    private fun getDocumentIdentifier(item: Any): DocumentId {
        val idString = getter
            .asSequence()
            .filter { it.annotations.any(IdAnnotation::class::isInstance) }
            .map { it.name to it.javaGetter!!.invoke(item) }
            .map { Property(PropertyKey(it.first), it.second) }
            .map { it.getStringRepresentation() }
            .ifEmpty { sequenceOf(UUID.randomUUID().toString()) }
            .reduce { acc, s -> acc + s }

        val uuid = UUID.nameUUIDFromBytes(idString.toByteArray())
        return DocumentId(uuid.toString(), getDocumentTypeIdentifier())
    }

    private fun getEdgeIdentifier(edge: Any): UUID {
        val idString = edge::class.declaredMemberProperties
                .asSequence()
                .filter { it.annotations.any(IdAnnotation::class::isInstance) }
                .map { it.name to it.javaGetter!!.invoke(edge) }
                .map { Property(PropertyKey(it.first), it.second) }
                .map { it.getStringRepresentation() }
                .ifEmpty { sequenceOf(UUID.randomUUID().toString()) }
                .reduce { acc, s -> acc + s }

        return UUID.nameUUIDFromBytes(idString.toByteArray())
    }

    private fun getMetadata(item: Any): Collection<Property<*>> {
        return getter
            .asSequence()
            .filter { it.annotations.any(PropertyAnnotation::class::isInstance) }
            .map { getName(it) to it.javaGetter!!.invoke(item) }
            .map { Property(PropertyKey(it.first), it.second) }
            .map { Property::class.cast(it) }
            .toList()
    }

    private fun getName(it: KProperty1<out Any, *>): String {
        val first = it.annotations.first(PropertyAnnotation::class::isInstance)
        if (first is PropertyAnnotation) {
            if (first.key.isNotEmpty()) return first.key
        }
        return it.name
    }
}

private fun toCollection(it: Any?) = if (it is Collection<*>) it else listOf(it)
