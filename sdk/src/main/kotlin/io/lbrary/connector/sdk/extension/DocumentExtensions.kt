package io.lbrary.connector.sdk.extension

import io.datalbry.precise.api.schema.document.Document
import io.lbrary.connector.sdk.consumer.AdditionMessageConsumer.Companion.CHECKSUM_FIELD
import io.lbrary.connector.sdk.state.DocumentState

fun Document.toDocumentState(): DocumentState {
    return DocumentState(this.id, this[CHECKSUM_FIELD].value as String? ?: "")
}
