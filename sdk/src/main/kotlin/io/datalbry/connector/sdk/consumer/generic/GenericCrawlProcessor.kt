package io.datalbry.connector.sdk.consumer.generic

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.connector.api.CrawlProcessor
import io.datalbry.connector.api.DocumentEdge
import io.datalbry.connector.api.DocumentNode
import io.datalbry.connector.api.Node
import io.datalbry.connector.api.document.DocumentCrawlProcessor
import io.datalbry.connector.api.document.DocumentProcessor
import org.slf4j.LoggerFactory

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
    : DocumentCrawlProcessor
{
    private val inner : Collection<ReflectionProcessor> = crawlProcessors.map { ReflectionProcessorImpl(it) }
    private val mapper: Collection<ItemMapper<Any>> = inner.map { GenericItemMapper(it.produces().kotlin) }

    private val jackson = jacksonObjectMapper()

    init {
        inner
            .groupBy { it.consumes() }
            .filter { (_, processors) -> processors.size > 1 }
            .forEach {
                log.warn(
                    "Multiple processors for [${it.key.simpleName}] found. " +
                            "This is supported, but might result into less stability," +
                            " as huge chunks of the source system are touched at once"
                )
            }
    }

    override fun process(value: DocumentEdge): Node<DocumentEdge, Document> {
        val pair = inner
            .filter { value.headers[TYPE_KEY] == it.consumes().name }
            .map { toObj(value.payload, it.consumes()) to it }
            .flatMap { (obj, processor) -> processor.process(obj) }
            .map { it to mapper.first{ m -> m.supports(it)} }
            .map { it.second.getDocuments(it.first) to it.second.getEdges(it.first) }
            .fold(emptySet<Document>() to emptySet<DocumentEdge>()) {
                    acc, i -> acc.first.union(i.first) to acc.second.union(i.second)
            }

        return DocumentNode(value.uuid, pair.first, pair.second)
    }

    private fun toObj(payload: Map<String, String>, type: Class<*>): Any {
        return jackson.convertValue(payload, jackson.typeFactory.constructType(type))
    }

    companion object {
        private val log = LoggerFactory.getLogger(GenericCrawlProcessor::class.java)

        const val TYPE_KEY = "type"
        const val UUID_KEY = "uuid"
        const val PRODUCER_KEY = "producer"
    }
}
