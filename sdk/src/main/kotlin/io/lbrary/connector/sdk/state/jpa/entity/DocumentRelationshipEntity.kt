package io.lbrary.connector.sdk.state.jpa.entity

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(DocumentRelationshipEntityId::class)
data class DocumentRelationshipEntity(
    @Id val node: UUID,
    @Id val documentKey: String,
    @Id val documentSchema: String,
    val revision: UUID
)

@Embeddable
data class DocumentRelationshipEntityId(
    val node: UUID,
    val documentKey: String,
    val documentSchema: String,
): Serializable
