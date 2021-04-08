package io.datalbry.connector.api.document

/**
 * The [DocumentProcessor] is the sophisticated entrypoint of the connector-sdk.
 *
 * Its being utilized by the generic connector-sdk which is able to find the correct implementation at runtime.
 *
 * @param Consumes is the type of the pojo the [DocumentProcessor] implementation is able to consume
 * @param Produces is the type of the pojo the [DocumentProcessor] implementation is able to produce
 *
 * _IMPORTANT:_
 * There is at least one implementation which has to be present at runtime.
 * [DocumentProcessor] which consumes [io.datalbry.connector.api.root.DocumentRoot] which indicates the start of the traversal.
 *
 * @see [io.datalbry.connector.api.root.DocumentRoot]
 *
 * @author timo gruen - 2020-12-27
 */
interface DocumentProcessor<Consumes, Produces> {

    /**
     * Processes an pojo of the type [Consumes] to produce a collection of pojos of the type [Produces]
     *
     * @param edge to consume
     * @return collection of POJOs of the type [Produces]
     */
    fun process(edge: Consumes): Collection<Produces>

}
