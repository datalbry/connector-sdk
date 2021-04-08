package io.datalbry.connector.sdk.consumer.generic.documents

import io.datalbry.connector.api.annotation.property.Children
import io.datalbry.connector.api.annotation.property.Id
import io.datalbry.connector.api.annotation.stereotype.Container

@Container
class ContainerWithChildren(
    @Id val key: String = "Container",
    @Children val children: Collection<Child>
)
