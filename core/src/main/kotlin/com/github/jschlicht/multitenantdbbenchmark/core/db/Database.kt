package com.github.jschlicht.multitenantdbbenchmark.core.db

import com.github.jschlicht.multitenantdbbenchmark.core.strategy.DistributedTable
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import org.jooq.DSLContext
import org.jooq.DataType
import org.jooq.Name
import org.jooq.SQLDialect
import org.jooq.impl.DSL.name
import org.testcontainers.containers.JdbcDatabaseContainer

sealed class Database(val key: String, val dialect: SQLDialect, val defaultSchema: String) {
    fun qualify(schema: String, table: String): Name {
        return if (schema != defaultSchema) {
            name(schema, table)
        } else {
            name(table)
        }
    }

    open fun setup(dsl: DSLContext) {
    }

    open fun supports(strategy: Strategy): Boolean {
        return strategy !is DistributedTable
    }

    abstract fun createContainer(): JdbcDatabaseContainer<*>
}
