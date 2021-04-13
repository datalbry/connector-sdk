package io.datalbry.connector.sdk.extension

import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer.Companion.CHECKSUM_FIELD
import io.datalbry.connector.sdk.state.DocumentState
import io.datalbry.precise.api.schema.document.Document

fun Document.toDocumentState(): DocumentState {
    return DocumentState(this.id, this[CHECKSUM_FIELD].value as String? ?: "")
}
