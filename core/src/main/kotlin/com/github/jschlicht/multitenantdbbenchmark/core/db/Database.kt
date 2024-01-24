package com.github.jschlicht.multitenantdbbenchmark.core.db

import com.github.jschlicht.multitenantdbbenchmark.core.strategy.DistributedTable
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import org.jooq.DSLContext
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

    open fun supportsForeignKeysWith(strategy: Strategy) : Boolean {
        return true
    }

    abstract fun createContainer(): JdbcDatabaseContainer<*>

    abstract fun manualPartitionCreation() : Boolean

    abstract fun hashPartition(column: String, partitionCount: Int) : String

    abstract fun listPartition(column: String, ids: List<Long>) : String

    open fun requiresSeparateIndexOnId(strategy: Strategy) : Boolean {
        return false
    }
}
