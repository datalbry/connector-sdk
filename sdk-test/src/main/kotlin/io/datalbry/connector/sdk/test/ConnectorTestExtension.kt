package io.datalbry.connector.sdk.test

import io.datalbry.connector.sdk.ConnectorProperties.Companion.ALXNDRIA_DATASOURCE_PROPERTY
import io.datalbry.connector.sdk.ConnectorProperties.Companion.ALXNDRIA_URI_PROPERTY
import io.datalbry.connector.sdk.test.container.PostgresContainer
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

    private val alxndria = AlxndriaContainer(getAlxndriaVersion())
    private val postgres = PostgresContainer(getPostgresVersion())

    override fun beforeAll(context: ExtensionContext) {
        alxndria.start()
        postgres.start()
        System.setProperty(PROPERTY_DATALBRY_PLATFORM_URI, "http://127.0.0.1:${alxndria.getPort()}")
        System.setProperty(ALXNDRIA_DATASOURCE_PROPERTY, TEST_DATASOURCE)
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create")
        System.setProperty(PROPERTY_SPRING_DATASOURCE_URL, postgres.jdbcUrl)
        System.setProperty(PROPERTY_SPRING_DATASOURCE_USERNAME, postgres.username)
        System.setProperty(PROPERTY_SPRING_DATASOURCE_PASSWORD, postgres.password)
    }

    override fun afterAll(context: ExtensionContext) {
        postgres.stop()
        alxndria.stop()
    }

    private fun getPostgresVersion() = getEnvOrDefault("test.postgres.container.version", "9.6.12")
    private fun getAlxndriaVersion() = getEnvOrDefault("test.alxndria.container.version", "0.0.3-SNAPSHOT")

    companion object {
        const val TEST_DATASOURCE = "test"
        const val PROPERTY_DATALBRY_PLATFORM_URI = ALXNDRIA_URI_PROPERTY
        const val PROPERTY_SPRING_DATASOURCE_URL = "spring.datasource.url"
        const val PROPERTY_SPRING_DATASOURCE_USERNAME = "spring.datasource.username"
        const val PROPERTY_SPRING_DATASOURCE_PASSWORD = "spring.datasource.password"
    }
}

private fun getEnvOrDefault(env: String, default: String): String {
    return try {
        System.getenv(env)
    } catch (e: NullPointerException) {
        default
    }
}
