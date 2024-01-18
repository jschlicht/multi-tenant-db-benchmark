package com.github.jschlicht.multitenantdbbenchmark.db

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

abstract class PostgresBase(key: String) : Database(key) {
    abstract val dockerImageName: DockerImageName

    override fun createContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(dockerImageName)
            .withCommand("postgres") // test-containers disables fsync by default
    }
}
