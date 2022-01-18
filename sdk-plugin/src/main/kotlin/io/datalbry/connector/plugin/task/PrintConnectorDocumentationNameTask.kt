package io.datalbry.connector.plugin.task

import io.datalbry.connector.plugin.ConnectorPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.util.prefixIfNot

/**
 * A simple gradle task which prints the connector name for the documentation at docs.datalbry.io.
 *
 * If the product name and vendor is the same the output will be: "${productName}-connector"
 * If they are different the output will be "${vendor}-${productName}-connector"
 */
open class PrintConnectorDocumentationNameTask : DefaultTask() {

    @TaskAction
    fun printConnectorDocumentationNameTask() {
        val extension = project.extensions.getByType(ConnectorPluginExtension::class.java)
        val name = "${extension.productName}-connector".prefixIfNot("${extension.vendor}-")
        println(name)
    }

}
