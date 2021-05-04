package io.datalbry.connector.sdk.test

import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.api.root.createRoot
import io.datalbry.connector.sdk.ConnectorApplication
import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.test.processor.TestRootProcessor
import io.datalbry.connector.sdk.test.util.await
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@Testcontainers
@SpringBootTest(classes = [ConnectorApplication::class])
@ExtendWith(value = [ConnectorTestExtension::class])
class ConnectorTestIntegrationTest {

    @Autowired lateinit var testProcessor: TestRootProcessor
    @Autowired lateinit var addition: Channel<DocumentEdge>

    @Test
    fun additionMessageConsumer_consume_hasBeenInvokedCorrectly() {
        addition.propagate(createRoot())

        await(
            await = { testProcessor.hasBeenInvoked },
            until = { it },
            assert = { assertTrue(it) },
            timeout = Duration.ofSeconds(60)
        )
    }
}
