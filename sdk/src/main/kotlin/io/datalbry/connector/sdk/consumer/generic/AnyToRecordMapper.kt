package io.datalbry.connector.sdk.consumer.generic

import io.datalbry.connector.sdk.util.annotatedWith
import io.datalbry.connector.sdk.util.isBasicFieldType
import io.datalbry.precise.api.schema.Exclude
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.precise.api.schema.document.generic.GenericRecord
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter

/**
 * [AnyToRecordMapper] is able to fetch information from POJOs and Kotlin (data) classes
 * to derive a [Record] from them
 *
 * Internally heavily relying on reflection to fetch the information properly
 *
 * @author timo gruen - 2021-04-09
 */
class AnyToRecordMapper {

    /**
     * Derives a [Record] from the POJO / Data Class [obj]
     *
     * @param obj to derive the record from
     *
     * @return newly created [Record]
     */
    fun getRecord(obj: Any): Record {
        val metadata = getMetadata(obj)
        val type = obj::class.simpleName
        return GenericRecord(type!!, metadata)
    }

    private fun getMetadata(item: Any): Set<Field<*>> {
        val getter = item::class.declaredMemberProperties
        val (basicFields, complexFields) = getter
            .asSequence()
            .filterNot { it.annotatedWith<Exclude>() }
            .map { it.name to it.javaGetter!!.invoke(item) }
            .map { (name, value) -> GenericField(name, value) }
            .partition { isBasicFieldType(it.value) }

        return basicFields.toSet() + complexFields
            .map {
                when(val value = it.value) {
                    is Array<*> -> GenericField(it.name, value.filterNotNull().map(this::getRecord))
                    is Collection<*> -> GenericField(it.name, value.filterNotNull().map(this::getRecord))
                    is Map<*, *> -> GenericField(it.name, GenericRecord(type="foo", fields = value.map { (k, v) -> GenericField(k.toString(), v) }.toSet()))
                    else -> GenericField(it.name, getRecord(value))
                }
            }
    }
}
