package io.lbrary.connector.sdk.state.jpa

import io.lbrary.connector.sdk.state.jpa.entity.DocumentRelationshipEntity
import io.lbrary.connector.sdk.state.jpa.entity.DocumentRelationshipEntityId
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.Repository
import java.util.*
import javax.persistence.LockModeType

interface DocumentRepository: Repository<DocumentRelationshipEntity, DocumentRelationshipEntityId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun save(entity: DocumentRelationshipEntity)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun removeByNode(node: UUID)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun removeByNodeAndDocumentKey(node: UUID, documentKey: String)

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findAllByNodeAndRevisionIsNot(node: UUID, revision: UUID): Collection<DocumentRelationshipEntity>

}
