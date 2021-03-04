package io.lbrary.connector.sdk.consumer.generic.documents

import io.lbrary.connector.api.annotation.property.Children
import io.lbrary.connector.api.annotation.property.Id
import io.lbrary.connector.api.annotation.stereotype.Container

@Container
class ContainerWithChildren(
    @Id val key: String = "Container",
    @Children val children: Collection<Child>
)
