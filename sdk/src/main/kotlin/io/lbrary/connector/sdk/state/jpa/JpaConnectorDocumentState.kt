package io.lbrary.connector.sdk.state.jpa

import io.lbrary.connector.sdk.state.ConnectorDocumentState
import io.lbrary.connector.sdk.state.Lock
import io.lbrary.connector.sdk.state.LockException
import io.lbrary.connector.sdk.state.NodeReference
import io.lbrary.connector.sdk.state.jpa.entity.DocumentRelationshipEntity
import io.lbrary.connector.sdk.state.jpa.entity.LockEntity
import io.lbrary.connector.sdk.state.jpa.entity.NodeRelationshipEntity
import io.lbrary.service.index.api.document.DocumentId
import io.lbrary.service.index.api.schema.DocumentSchemaId
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
    override fun put(parent: NodeReference, child: DocumentId, lock: Lock) {
        runIfLocked(parent, lock) {
            val entity = DocumentRelationshipEntity(
                node = parent.uuid,
                documentKey = child.key,
                documentSchema = child.type.key,
                revision = lock.uuid)
            documentRepository.save(entity)
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun remove(parent: NodeReference, child: DocumentId, lock: Lock) {
        runIfLocked(parent, lock) {
            documentRepository.removeByNodeAndDocumentKey(parent.uuid, child.key)
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
    override fun getUnseenNodes(node: NodeReference, lock: Lock): Collection<NodeReference> {
        return runIfLocked(node, lock) {
            jobRepository
                    .findAllByParentAndRevisionIsNot(node.uuid, lock.uuid)
                    .map { NodeReference(it.child) }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun getUnseenDocuments(node: NodeReference, lock: Lock): Collection<DocumentId> {
        return runIfLocked(node, lock) {
            documentRepository
                .findAllByNodeAndRevisionIsNot(node.uuid, lock.uuid)
                .map { DocumentId(it.documentKey, DocumentSchemaId(it.documentSchema)) }
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
