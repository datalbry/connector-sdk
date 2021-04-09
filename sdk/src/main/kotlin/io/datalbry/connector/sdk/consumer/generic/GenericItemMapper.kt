package io.datalbry.connector.sdk.consumer.generic

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.precise.api.schema.Exclude
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.generic.GenericDocument
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.api.annotation.property.Checksum
import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer.Companion.CHECKSUM_FIELD
import io.datalbry.connector.sdk.consumer.generic.GenericCrawlProcessor.Companion.TYPE_KEY
import io.datalbry.connector.sdk.util.annotatedWith
import io.datalbry.connector.sdk.util.isBasicFieldType
import io.datalbry.precise.api.schema.document.generic.GenericRecord
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter
import io.datalbry.connector.api.annotation.property.Children as ChildrenAnnotation
import io.datalbry.connector.api.annotation.property.Id as IdAnnotation
import io.datalbry.connector.api.annotation.stereotype.Document as DocumentAnnotation

/**
 * The GenericItemMapper is able to map a candidate of type `Any` to a collection of documents
 * as well as to a collection of edges.
 *
 * Proceed with caution. The class is highly complicated and is using reflection excessively.
 *
 * @author timo gruen - 2020-11-15
 */
class GenericItemMapper(private val clazz: KClass<*>): ItemMapper<Any> {
    private val getter = clazz.declaredMemberProperties
    private val jackson = jacksonObjectMapper()
    private val recordMapper = AnyToRecordMapper()

    // returnType = Collection<*> != Collection<String>

    override fun getDocuments(item: Any): Collection<Document> {
        assert(supports(item)) { "${this.clazz.simpleName} does not support [${item.javaClass}]." }

        if (clazz.annotations.none(DocumentAnnotation::class::isInstance)) return emptyList()

        val id = getId(item)
        val checksum = getChecksum(item)
        val record = recordMapper.getRecord(item)

        val document = GenericDocument(record.type, id, record.fields + checksum)

        return listOf(document)
    }

    private fun getChecksum(item: Any): Field<String> {
        val checksum = getter
            .asSequence()
            .filter { it.annotatedWith<Checksum>() }
            .map { it.name to it.javaGetter!!.invoke(item) }
            .map { it.toString() }
            .sorted()
            .fold("") { acc, s -> acc + s }
        return GenericField(CHECKSUM_FIELD, checksum)
    }

    override fun getEdges(item: Any): Collection<DocumentEdge> {
        return getter
                .asSequence()
                .filter { it.annotatedWith<ChildrenAnnotation>() }
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

    private fun getId(item: Any): String {
        val idString = getter
            .asSequence()
            .filter { it.annotatedWith<IdAnnotation>() }
            .map { it.javaGetter!!.invoke(item) }
            .map { it.toString() }
            .ifEmpty { sequenceOf(UUID.randomUUID().toString()) }
            .reduce { acc, s -> acc + s }

        val uuid = UUID.nameUUIDFromBytes(idString.toByteArray())
        return uuid.toString()
    }

    private fun getEdgeIdentifier(edge: Any): UUID {
        val idString = edge::class.declaredMemberProperties
            .asSequence()
            .filter { it.annotatedWith<IdAnnotation>() }
            .map { it.javaGetter!!.invoke(edge) }
            .map { it.toString() }
            .ifEmpty { sequenceOf(UUID.randomUUID().toString()) }
            .reduce { acc, s -> acc + s }

        return UUID.nameUUIDFromBytes(idString.toByteArray())
    }


}

private fun toCollection(it: Any?) = if (it is Collection<*>) it else listOf(it)
