package com.github.jschlicht.multitenantdbbenchmark.core.db

import com.github.jschlicht.multitenantdbbenchmark.core.strategy.*
import org.jooq.SQLDialect

abstract class MySQLBase(key: String, dialect: SQLDialect, defaultSchema: String) :
    Database(key, dialect, defaultSchema) {

    override fun supports(strategy: Strategy): Boolean {
        return when (strategy) {
            Namespace,
            Normalized,
            TenantIdComposite,
            TenantIdSimple -> true

            DistributedTable -> false // citus only

            PartitionHash,
            PartitionList -> false // technically possible, but we lose auto-incrementing keys and foreign keys.
        }
    }
}