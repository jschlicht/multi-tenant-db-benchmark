package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.db.Citus
import com.github.jschlicht.multitenantdbbenchmark.db.MariaDB
import com.github.jschlicht.multitenantdbbenchmark.db.MySQL
import com.github.jschlicht.multitenantdbbenchmark.db.Postgres
import com.github.jschlicht.multitenantdbbenchmark.strategy.*
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() {
    val allDatabases = listOf(MariaDB, MySQL, Postgres, Citus)
    val allStrategies = listOf(Normalized, TenantIdSimple, TenantIdComposite, PartitionHash, PartitionList, Namespace, DistributedTable)

    allDatabases.forEach { database ->
        allStrategies.forEach { strategy ->
            if (database.supports(strategy)) {
                logger.info { "Running benchmark for ${database.key} with ${strategy.key}" }
                BenchmarkContext(database, strategy).run()
            } else {
                logger.info { "Skipping benchmark for ${database.key} with ${strategy.key}" }
            }
        }
    }
}