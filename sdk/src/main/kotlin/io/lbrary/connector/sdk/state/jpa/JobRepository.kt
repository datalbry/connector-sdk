package io.lbrary.connector.sdk.state.jpa

import io.lbrary.connector.sdk.state.jpa.entity.NodeRelationshipEntity
import io.lbrary.connector.sdk.state.jpa.entity.NodeRelationshipEntityId
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.Repository
import java.util.*
import javax.persistence.LockModeType

interface JobRepository : Repository<NodeRelationshipEntity, NodeRelationshipEntityId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun save(entity: NodeRelationshipEntity)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun removeByParent(parentUuid: UUID)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun removeByParentAndChild(parentUuid: UUID, childUuid: UUID)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAll(): Collection<NodeRelationshipEntity>

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findAllByParentAndRevisionIsNot(parentUuid: UUID, revision: UUID): Collection<NodeRelationshipEntity>

}
