package io.datalbry.connector.autoconfigure

import io.datalbry.connector.api.document.DocumentCrawlProcessor
import io.datalbry.connector.api.document.DocumentProcessor
import io.datalbry.connector.sdk.consumer.generic.GenericCrawlProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GenericCrawlProcessorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DocumentCrawlProcessor::class)
    open fun genericCrawlProcessor(
        crawlProcessors: Collection<DocumentProcessor<*, *>>
    ): DocumentCrawlProcessor {
        return GenericCrawlProcessor(crawlProcessors)
    }

}
