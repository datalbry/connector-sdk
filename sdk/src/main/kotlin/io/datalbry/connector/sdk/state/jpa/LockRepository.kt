package io.datalbry.connector.sdk.state.jpa

import io.datalbry.connector.sdk.state.jpa.entity.LockEntity
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.Repository
import java.util.*
import javax.persistence.LockModeType

interface LockRepository: Repository<LockEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun save(entity: LockEntity)

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun existsByNode(node: UUID): Boolean

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun existsByNodeAndUuid(node: UUID, uuid: UUID): Boolean

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun deleteByNodeAndUuid(node: UUID, uuid: UUID)

}
