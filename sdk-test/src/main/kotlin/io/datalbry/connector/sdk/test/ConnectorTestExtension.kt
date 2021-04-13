package io.datalbry.connector.sdk.test

import io.datalbry.testcontainers.bigtable.AlxndriaContainer
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * [ConnectorTestExtension] provides a convenient entry point for connector tests
 *
 *  Primarily runs both - the Postgres container, as well as the [AlxndriaContainer] and sets all required properties
 *
 * @author timo gruen - 2021-04-13
 */
class ConnectorTestExtension: BeforeAllCallback, AfterAllCallback  {

    private val alxndria = AlxndriaContainer("0.0.3-SNAPSHOT")

    override fun beforeAll(context: ExtensionContext) {
        alxndria.start()
        System.setProperty("io.datalbry.platform.uri", "http://127.0.0.1:${alxndria.getPort()}")
        System.setProperty("io.datalbry.datasource.key", "test")
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create")
    }

    override fun afterAll(context: ExtensionContext) {
        alxndria.stop()
    }
}
