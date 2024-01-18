package com.github.jschlicht.multitenantdbbenchmark.db

import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy
import org.testcontainers.containers.PostgreSQLContainer

data object Citus : PostgresBase("citus", "citusdata/citus") {
    override fun supports(strategy: Strategy): Boolean {
        return true
    }

    override fun createContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(containerName)
    }
}