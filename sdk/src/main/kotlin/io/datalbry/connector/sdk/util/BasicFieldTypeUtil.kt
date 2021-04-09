package io.datalbry.connector.sdk.util

import io.datalbry.precise.api.schema.field.BasicFieldType
import java.util.*

/**
 * Checks whether an object is a BasicFieldType value or not
 *
 * Uses the [fieldTypeToClassesMap] to check if the object is of any specific class type,
 * which is being defined as [BasicFieldType]
 *
 * @param obj to check
 *
 * @return true if the obj is a [BasicFieldType], otherwise false
 *
 * @author timo gruen - 2021-04-09
 */
fun isBasicFieldType(obj: Any?): Boolean {
    return when (obj) {
        is Collection<*> -> obj.all(::isBasicFieldType)
        is Array<*> -> obj.all(::isBasicFieldType)
        is Optional<*> -> obj.map(::isBasicFieldType).orElse(true)
        else -> getBasicFieldType(obj) != null
    }
}

private val fieldTypeToClassesMap = mapOf(
    BasicFieldType.BOOLEAN to arrayOf(Boolean::class),
    BasicFieldType.BYTE to arrayOf(Byte::class, UByte::class),
    BasicFieldType.FLOAT to arrayOf(Float::class),
    BasicFieldType.DOUBLE to arrayOf(Double::class),
    BasicFieldType.INT to arrayOf(Short::class, Int::class),
    BasicFieldType.LONG to arrayOf(Long::class),
    BasicFieldType.STRING to arrayOf(String::class, CharSequence::class)
)

private fun getBasicFieldType(obj: Any?): BasicFieldType? {
    return fieldTypeToClassesMap.entries.firstOrNull { it.value.any { v -> v.isInstance(obj) } }?.key
}
