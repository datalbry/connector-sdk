package io.lbrary.connector.sdk.consumer.generic

import io.lbrary.connector.api.document.DocumentProcessor

interface ReflectionProcessor: DocumentProcessor<Any, Any> {

    fun consumes(): Class<*>

    fun produces(): Class<*>

    fun name(): String
}
