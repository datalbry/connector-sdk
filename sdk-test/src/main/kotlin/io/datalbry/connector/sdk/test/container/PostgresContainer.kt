package io.datalbry.connector.sdk.test.container

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

/**
 * Wrapper around the [PostgreSQLContainer]
 *
 * necessary due to the [PostgreSQLContainer.self] generic reference.
 *
 * @author timo gruen - 2021-04-20
 */
class PostgresContainer(version: String): PostgreSQLContainer<PostgresContainer>(
    DockerImageName.parse("postgres:$version")
)
