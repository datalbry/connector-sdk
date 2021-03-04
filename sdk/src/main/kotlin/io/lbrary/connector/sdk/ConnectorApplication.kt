package io.lbrary.connector.sdk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["io.lbrary"])
open class ConnectorApplication

fun main() {
    runApplication<ConnectorApplication>()
}
