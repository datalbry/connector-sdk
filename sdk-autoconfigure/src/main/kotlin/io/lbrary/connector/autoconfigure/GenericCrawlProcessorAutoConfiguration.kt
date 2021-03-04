package io.lbrary.connector.autoconfigure

import io.lbrary.connector.api.document.DocumentProcessor
import io.lbrary.connector.sdk.consumer.generic.GenericCrawlProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(GenericCrawlProcessor::class)
open class GenericCrawlProcessorAutoConfiguration {

    @Bean
    open fun genericCrawlProcessor(
        crawlProcessors: Collection<DocumentProcessor<*, *>>
    ): GenericCrawlProcessor {
        return GenericCrawlProcessor(crawlProcessors)
    }

}
