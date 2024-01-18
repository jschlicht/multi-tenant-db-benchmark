package com.github.jschlicht.multitenantdbbenchmark.db

import org.testcontainers.containers.PostgreSQLContainer

data object Postgres : PostgresBase("postgres", "postgres") {
    override fun createContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(containerName)
    }
}