package io.datalbry.connector.sdk.consumer.generic

import io.datalbry.connector.api.CrawlProcessor
import io.datalbry.connector.api.document.DocumentProcessor
import org.springframework.core.ResolvableType
import java.lang.reflect.Method

/**
 * The [ReflectionProcessorImpl] is able to consume a [CrawlProcessor]
 * without it's type information and to provide a convenient functions to use for
 * the dynamic type handling.
 *
 * - Reflection is pretty expensive at runtime, so we are reducing the overhead to a very minimal
 *   for each call. Reflection happens especially at construction time. Only the method calling
 *   via reflection is done at Runtime.
 * - The class is being brought to provide a layer of convenience for the writing of Connectors using the
 *   Connector SDK.
 *
 * The main entrypoint for the generic processing is the [GenericCrawlProcessor] which is the only consumer of
 * [ReflectionProcessor]s.
 *
 * @param inner [CrawlProcessor] without any generic type information
 *
 * @author timo gruen - 2020-11-10
 */
class ReflectionProcessorImpl(private val inner: DocumentProcessor<*, *>): ReflectionProcessor {

    private val type: ResolvableType = ResolvableType.forClass(DocumentProcessor::class.java, inner::class.java)

    private val name: String = inner::class.qualifiedName!!

    private val consumes: Class<*> = type.generics[0].resolve()!!
    private val produces: Class<*> = type.generics[1].resolve()!!

    private val process: Method = inner.javaClass.methods.first { it.name == CrawlProcessor<*, *>::process.name }

    @Suppress("UNCHECKED_CAST")
    override fun process(edge: Any): Collection<Any> {
        return process.invoke(inner, edge) as Collection<Any>
    }

    override fun consumes(): Class<*> {
        return this.consumes
    }

    override fun produces(): Class<*> {
        return this.produces
    }

    override fun name(): String {
        return this.name
    }
}
