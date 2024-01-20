package com.github.jschlicht.multitenantdbbenchmark

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.boolean
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import com.github.jschlicht.multitenantdbbenchmark.db.*
import com.github.jschlicht.multitenantdbbenchmark.strategy.*
import com.github.jschlicht.multitenantdbbenchmark.util.MdcKey
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import org.slf4j.MDC
import org.testcontainers.utility.TestcontainersConfiguration
import java.util.*

private val logger = KotlinLogging.logger {}

class Benchmark : CliktCommand() {
    val databases by option("--databases", "-d", help = "Select which databases to benchmark")
        .choice(databaseChoices)
        .split(",")
        .defaultLazy { databaseChoices.values.toList() }
        .check("at least one database must be selected") { it.isNotEmpty() }

    val output by option("--output", "-o", help = "Output generated .sql files to this folder")
        .path(canBeFile = false, canBeDir = true, mustExist = false, mustBeWritable = true)

    val strategies by option("--strategies", "-s", help = "Select which multi-tenant strategies to benchmark")
        .choice(strategyChoices)
        .split(",")
        .defaultLazy { strategyChoices.values.toList() }
        .check("at least one strategy must be selected") { it.isNotEmpty() }

    val verbose by option("--verbose", "-v", help = "Print generated SQL to stdout")
        .flag(default = false)

    override fun run() {
        databases.forEach { database ->
            strategies.forEach { strategy ->
                runStrategyForDb(strategy, database)
            }
        }
    }

    private fun runStrategyForDb(strategy: Strategy, database: Database) {
        withLoggingContext(MdcKey.db to database.key, MdcKey.strategy to strategy.key) {
            if (database.supports(strategy)) {
                logger.info { "Running benchmark" }

                BenchmarkContext(database, strategy, output, verbose).use {
                    it.run()
                }
            } else {
                logger.info { "Skipping benchmark" }
            }
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
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        Benchmark().main(args)
    }
}
