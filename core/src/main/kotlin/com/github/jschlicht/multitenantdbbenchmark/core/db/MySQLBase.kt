package com.github.jschlicht.multitenantdbbenchmark.core.db

import com.github.jschlicht.multitenantdbbenchmark.core.strategy.*
import org.jooq.SQLDialect

abstract class MySQLBase(key: String, dialect: SQLDialect, defaultSchema: String) :
    Database(key, dialect, defaultSchema) {

    override val overrideUsername: String? = "root"

    override fun supportsGlobalTableForeignKeysWith(strategy: Strategy): Boolean {
        return supportsForeignKeysWith(strategy) && when (strategy) {
            Namespace -> false // can't foreign key across databases
            else -> true
        }
    }

    override fun supportsForeignKeysWith(strategy: Strategy): Boolean {
        return when (strategy) {
            PartitionHash,
            PartitionList -> false
            else -> true
        }
    }

    override fun requiresSeparateIndexOnId(strategy: Strategy): Boolean {
        return when (strategy) {
            DistributedTable -> false
            Namespace -> false
            Normalized -> false
            PartitionHash -> true
            PartitionList -> true
            TenantIdComposite -> true
            TenantIdSimple -> false
        }
    }

    override fun manualPartitionCreation(): Boolean {
        return false
    }

    override fun hashPartition(column: String, partitionCount: Int): String {
        return "PARTITION BY HASH ($column) PARTITIONS $partitionCount"
    }

    override fun listPartition(column: String, ids: List<Long>): String {
        val partitions = ids.joinToString(prefix = "\n", separator = ",\n", postfix = "\n") {
            "  PARTITION p$it VALUES IN ($it)"
        }

        return "PARTITION BY LIST ($column) ($partitions)"
    }
}
