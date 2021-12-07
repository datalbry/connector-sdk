package io.datalbry.connector.sdk.consumer.generic

import io.datalbry.connector.api.document.DocumentProcessor

/**
 * The [ReflectionProcessor] provides type information about another processor.
 */
interface ReflectionProcessor: DocumentProcessor<Any, Any> {

    /**
     * The [consumes] method returns the input type of processor.
     */
    fun consumes(): Class<*>

    /**
     * The [produces] method returns the output type of processor.
     */
    fun produces(): Class<*>

    /**
     * The [name] method returns the name of a processor.
     */
    fun name(): String
}
