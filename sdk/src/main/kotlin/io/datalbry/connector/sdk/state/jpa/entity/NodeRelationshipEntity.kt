package io.datalbry.connector.sdk.state.jpa.entity

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(NodeRelationshipEntityId::class)
data class NodeRelationshipEntity(
    @Id val parent: UUID,
    @Id val child: UUID,
    val revision: UUID
)

@Embeddable
data class NodeRelationshipEntityId(
    val parent: UUID,
    val child: UUID
): Serializable
