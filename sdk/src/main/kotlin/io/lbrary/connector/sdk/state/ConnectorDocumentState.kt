package io.lbrary.connector.sdk.state

interface ConnectorDocumentState {

    @Throws(LockException::class)
    fun lock(node: NodeReference): Lock

    @Throws(LockException::class)
    fun put(parent: NodeReference, child: NodeReference, lock: Lock)

    @Throws(LockException::class)
    fun put(parent: NodeReference, doc: DocumentState, lock: Lock)

    @Throws(LockException::class)
    fun remove(parent: NodeReference, child: NodeReference, lock: Lock)

    @Throws(LockException::class)
    fun remove(parent: NodeReference, docId: String, lock: Lock)

    @Throws(LockException::class)
    fun getChecksum(parent: NodeReference, docId: String, lock: Lock): String

    @Throws(LockException::class)
    fun remove(node: NodeReference, lock: Lock)

    @Throws(LockException::class)
    fun getUnseenNodes(node: NodeReference, lock: Lock): Collection<NodeReference>

    @Throws(LockException::class)
    fun getUnseenDocuments(node: NodeReference, lock: Lock): Collection<String>

    @Throws(LockException::class)
    fun release(node: NodeReference, lock: Lock)
}
