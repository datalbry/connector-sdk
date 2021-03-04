package io.lbrary.connector.sdk.state

import io.lbrary.service.index.api.document.DocumentId

interface ConnectorDocumentState {

    @Throws(LockException::class)
    fun lock(node: NodeReference): Lock

    @Throws(LockException::class)
    fun put(parent: NodeReference, child: NodeReference, lock: Lock)

    @Throws(LockException::class)
    fun put(parent: NodeReference, child: DocumentId, lock: Lock)

    @Throws(LockException::class)
    fun remove(parent: NodeReference, child: NodeReference, lock: Lock)

    @Throws(LockException::class)
    fun remove(parent: NodeReference, child: DocumentId, lock: Lock)

    @Throws(LockException::class)
    fun remove(node: NodeReference, lock: Lock)

    @Throws(LockException::class)
    fun getUnseenNodes(node: NodeReference, lock: Lock): Collection<NodeReference>

    @Throws(LockException::class)
    fun getUnseenDocuments(node: NodeReference, lock: Lock): Collection<DocumentId>

    @Throws(LockException::class)
    fun release(node: NodeReference, lock: Lock)
}
