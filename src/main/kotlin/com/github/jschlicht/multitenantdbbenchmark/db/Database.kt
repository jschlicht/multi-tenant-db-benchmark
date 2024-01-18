package com.github.jschlicht.multitenantdbbenchmark.db

import com.github.jschlicht.multitenantdbbenchmark.strategy.DistributedTable
import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy
import org.testcontainers.containers.JdbcDatabaseContainer

sealed class Database(val key: String) {
    open fun supports(strategy: Strategy): Boolean {
        return strategy !is DistributedTable
    }

    abstract fun createContainer(): JdbcDatabaseContainer<*>
}
