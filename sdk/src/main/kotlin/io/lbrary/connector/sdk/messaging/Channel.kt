package io.lbrary.connector.sdk.messaging

interface Channel<T> {

    fun propagate(message: T)

    fun hasElement(): Boolean

    companion object {
        const val DESTINATION_NODE_ADDITION = "document_addition"
        const val DESTINATION_NODE_DELETION = "document_deletion"
    }
}
