package io.datalbry.connector.plugin.util

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

/**
 * Short circuit to access the property function without passing the actual type information
 */
inline fun <reified Type> ObjectFactory.property(): Property<Type> {
    return this.property(Type::class.java)
}

/**
 * Short circuit to access the property function without passing the actual type information
 */
inline fun <reified Type> ObjectFactory.domainObjectContainer(): NamedDomainObjectContainer<Type> {
    return this.domainObjectContainer(Type::class.java)
}
