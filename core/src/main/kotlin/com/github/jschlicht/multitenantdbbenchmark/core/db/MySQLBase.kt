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
            PartitionList -> true // TODO: Remove comment: technically possible, but we lose auto-incrementing keys and foreign keys.
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
            PartitionHash,
            PartitionList -> true
            else -> false
        }
    }

    override fun manualPartitionCreation(): Boolean {
        return false
    }

    override fun hashPartition(column: String, partitionCount: Int): String {
        return "PARTITION BY HASH (${column}) PARTITIONS $partitionCount"
    }

    override fun listPartition(column: String, ids: List<Long>): String {
        val partitions = ids.joinToString(prefix = "\n", separator = ",\n", postfix = "\n") {
            "  PARTITION p$it VALUES IN ($it)"
        }

        return "PARTITION BY LIST ($column) ($partitions)"
    }
}