package io.lbrary.connector.sdk.state.jpa.entity

import java.time.ZonedDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class LockEntity(
    @Id val node: UUID,
    val uuid: UUID,
    val issued: ZonedDateTime
)
