package io.lbrary.connector.sdk.consumer.generic

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.lbrary.connector.api.CrawlProcessor
import io.lbrary.connector.api.DocumentEdge
import io.lbrary.connector.api.DocumentNode
import io.lbrary.connector.api.Node
import io.lbrary.connector.api.document.DocumentProcessor
import io.lbrary.service.index.api.document.Document
import java.util.*

/**
 * The [GenericCrawlProcessor] is the main entry point for the high level layer of the Connector SDK.
 * It's being used for handling generic [CrawlProcessor] without their type information.
 *
 * Internally using reflection on each of those [CrawlProcessor]s. All [CrawlProcessor] are being wrapped in a
 * [ReflectionProcessor] to provide the type information at runtime.
 *
 *  - [ReflectionProcessor] are being implemented by [ReflectionProcessorImpl] which is using quiet
 *    extensively reflection. As usually reflection is slow at runtime, so most reflection is done at
 *    construction time and only the "invoke" call of the [Processor.process] method is being done at
 *    actual Runtime using reflection.
 *
 * @param crawlProcessors to wrap with reflection based calls
 *
 * @author timo gruen - 2020-11-10
 */
class GenericCrawlProcessor(
        private val crawlProcessors: Collection<DocumentProcessor<*, *>>
)
    : CrawlProcessor<DocumentEdge, Document>
{
    private val inner : Collection<ReflectionProcessor> = crawlProcessors.map { ReflectionProcessorImpl(it) }
    private val mapper: Collection<ItemMapper<Any>> = inner.map { GenericItemMapper(it.produces().kotlin) }

    private val jackson = jacksonObjectMapper()

    override fun process(value: DocumentEdge): Node<DocumentEdge, Document> {
        val processor = inner.first { value.headers[TYPE_KEY] == it.consumes().name }
        val typedObj = toObj(value.payload, processor.consumes())
        val genericDocuments = processor.process(typedObj)

        val pair = genericDocuments
            .map { it to mapper.first{ m -> m.supports(it)} }
            .map { it.second.getDocuments(it.first) to it.second.getEdges(it.first) }
            .reduce { acc, i -> acc.first.union(i.first) to acc.second.union(i.second) }

        return DocumentNode(value.uuid, pair.first, pair.second)
    }

    private fun toObj(payload: Map<String, String>, type: Class<*>): Any {
        return jackson.convertValue(payload, jackson.typeFactory.constructType(type))
    }

    companion object {
        const val TYPE_KEY = "type"
        const val UUID_KEY = "uuid"
        const val PRODUCER_KEY = "producer"
    }
}
