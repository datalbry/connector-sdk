package io.datalbry.connector.sdk.test.processor

import io.datalbry.connector.api.document.DocumentProcessor
import io.datalbry.connector.api.root.DocumentRoot
import org.springframework.stereotype.Component

@Component
class TestRootProcessor: DocumentProcessor<DocumentRoot, Any> {

    var hasBeenInvoked = false

    override fun process(edge: DocumentRoot): Collection<Any> {
        hasBeenInvoked = true
        return emptyList()
    }
}
