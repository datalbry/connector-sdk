package io.datalbry.connector.autoconfigure.jms

import io.datalbry.alxndria.client.api.IndexClient
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.connector.api.CrawlProcessor
import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.sdk.consumer.AdditionMessageConsumer
import io.datalbry.connector.sdk.consumer.DeletionMessageConsumer
import io.datalbry.connector.sdk.messaging.Channel
import io.datalbry.connector.sdk.state.ConnectorDocumentState
import io.datalbry.connector.sdk.state.NodeReference
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(AdditionMessageConsumer::class, DeletionMessageConsumer::class)
open class MessageConsumerAutoConfiguration {

    @Bean
    open fun additionMessageConsumer(
        index: IndexClient,
        processor: CrawlProcessor<DocumentEdge, Document>,
        deletions: Channel<NodeReference>,
        additions: Channel<DocumentEdge>,
        state: ConnectorDocumentState
    ): AdditionMessageConsumer {
        return AdditionMessageConsumer(index, processor, deletions, additions, state)
    }

    @Bean
    open fun deletionMessageConsumer(
        index: IndexClient,
        channel: Channel<NodeReference>,
        state: ConnectorDocumentState
    ): DeletionMessageConsumer {
        return DeletionMessageConsumer(index, channel, state)
    }
}
