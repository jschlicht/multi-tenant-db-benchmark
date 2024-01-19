package com.github.jschlicht.multitenantdbbenchmark

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import com.github.jschlicht.multitenantdbbenchmark.db.*
import com.github.jschlicht.multitenantdbbenchmark.strategy.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.slf4j.MDC
import org.testcontainers.utility.TestcontainersConfiguration

private val logger = KotlinLogging.logger {}

class Benchmark : CliktCommand() {
    val databases by option(help = "Select which databases to benchmark")
        .choice(databaseChoices)
        .split(",")
        .defaultLazy { databaseChoices.values.toList() }
        .check("at least one database must be selected") { it.isNotEmpty() }

    val output by option(help = "Output generated .sql files to this folder")
        .path(canBeFile = false, canBeDir = true, mustExist = false, mustBeWritable = true)

    val strategies by option(help = "Select which multi-tenant strategies to benchmark")
        .choice(strategyChoices)
        .split(",")
        .defaultLazy { strategyChoices.values.toList() }
        .check("at least one strategy must be selected") { it.isNotEmpty() }

    override fun run() {
        databases.forEach { database ->
            strategies.forEach { strategy ->
                runStrategyForDb(strategy, database)
            }
        }
    }

    private fun runStrategyForDb(strategy: Strategy, database: Database) {
        MDC.put("db", database.key)
        MDC.put("strategy", strategy.key)

        if (database.supports(strategy)) {
            logger.info { "Running benchmark" }

            BenchmarkContext(database, strategy, output).use {
                it.run()
            }
        } else {
            logger.info { "Skipping benchmark" }
        }
    }

    companion object {
        private val databaseChoices = listOf(MariaDB, MySQL, Postgres, Citus).associateBy { it.key }
        private val strategyChoices = listOf(
            Normalized,
            TenantIdSimple,
            TenantIdComposite,
            PartitionHash,
            PartitionList,
            Namespace,
            DistributedTable
        ).associateBy { it.key }
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Benchmark().main(args)
    }
}
