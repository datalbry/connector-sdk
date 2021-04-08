package io.datalbry.connector.sdk.state.jpa

import io.datalbry.connector.sdk.state.*
import io.datalbry.connector.sdk.state.jpa.entity.DocumentRelationshipEntity
import io.datalbry.connector.sdk.state.jpa.entity.LockEntity
import io.datalbry.connector.sdk.state.jpa.entity.NodeRelationshipEntity
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.*

open class JpaConnectorDocumentState(
    private val jobRepository: JobRepository,
    private val documentRepository: DocumentRepository,
    private val lockRepository: LockRepository
)
    : ConnectorDocumentState
{

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun lock(node: NodeReference): Lock {
        if (!lockRepository.existsByNode(node.uuid)) {
            val entity = LockEntity(
                node = node.uuid,
                uuid = UUID.randomUUID(),
                issued = ZonedDateTime.now())
            lockRepository.save(entity)
            return Lock(entity.uuid)
        } else {
            throw LockException("Job[${node.uuid}] is already being locked.")
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun put(parent: NodeReference, child: NodeReference, lock: Lock) {
        runIfLocked(parent, lock) {
            val entity = NodeRelationshipEntity(
                parent = parent.uuid,
                child = child.uuid,
                revision = lock.uuid)
            jobRepository.save(entity)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun put(parent: NodeReference, doc: DocumentState, lock: Lock) {
        runIfLocked(parent, lock) {
            val entity = DocumentRelationshipEntity(
                node = parent.uuid,
                documentKey = doc.id,
                documentChecksum = doc.checksum,
                revision = lock.uuid)
            documentRepository.save(entity)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun remove(parent: NodeReference, docId: String, lock: Lock) {
        runIfLocked(parent, lock) {
            documentRepository.removeByNodeAndDocumentKey(parent.uuid, docId)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun remove(parent: NodeReference, child: NodeReference, lock: Lock) {
        runIfLocked(parent, lock) {
            jobRepository.removeByParentAndChild(parent.uuid, child.uuid)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun remove(node: NodeReference, lock: Lock) {
        runIfLocked(node, lock) {
            documentRepository.removeByNode(node.uuid)
            jobRepository.removeByParent(node.uuid)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun getChecksum(parent: NodeReference, docId: String, lock: Lock): String {
        return runIfLocked(parent, lock) {
            documentRepository.getByNodeAndDocumentKey(parent.uuid, docId).documentChecksum
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun getUnseenNodes(node: NodeReference, lock: Lock): Collection<NodeReference> {
        return runIfLocked(node, lock) {
            jobRepository
                    .findAllByParentAndRevisionIsNot(node.uuid, lock.uuid)
                    .map { NodeReference(it.child) }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun getUnseenDocuments(node: NodeReference, lock: Lock): Collection<String> {
        return runIfLocked(node, lock) {
            documentRepository
                .findAllByNodeAndRevisionIsNot(node.uuid, lock.uuid)
                .map { it.documentKey }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun release(node: NodeReference, lock: Lock) {
        return runIfLocked(node, lock) {
            lockRepository.deleteByNodeAndUuid(node.uuid, lock.uuid)
        }
    }

    private fun <T> runIfLocked(parent: NodeReference, lock: Lock, runnable: () -> T): T {
        if (lockRepository.existsByNodeAndUuid(parent.uuid, lock.uuid)) {
            return runnable()
        } else {
            throw LockException("Job[${parent.uuid}] is not being locked by Lock[${lock.uuid}].")
        }
    }
}
