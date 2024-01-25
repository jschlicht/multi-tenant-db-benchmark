package com.github.jschlicht.multitenantdbbenchmark

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.*
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.*
import com.github.jschlicht.multitenantdbbenchmark.core.util.MdcKey
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import java.util.*

private val logger = KotlinLogging.logger {}

private const val DEFAULT_HASH_PARTITION_COUNT = 64
private const val DEFAULT_TENANT_COUNT = 1000

class Benchmark : CliktCommand() {
    val databases by option("--databases", "-d", help = "Select which databases to benchmark")
        .choice(databaseChoicesByKey)
        .split(",")
        .defaultLazy { BenchmarkRunner.defaultDatabases }
        .check("at least one database must be selected") { it.isNotEmpty() }

    val output by option("--output", "-o", help = "Output generated .sql files to this folder")
        .path(canBeFile = false, canBeDir = true, mustExist = false, mustBeWritable = true)

    val hashPartitionCount by option("--partitions", "-p", help = "Number of partitions to use for hash partitioning")
        .int()
        .default(DEFAULT_HASH_PARTITION_COUNT)
        .check("must be greater than 0") { it > 0 }

    val strategies by option("--strategies", "-s", help = "Select which multi-tenant strategies to benchmark")
        .choice(strategyChoicesByKey)
        .split(",")
        .defaultLazy { strategyChoicesByKey.values.toList() }
        .check("at least one strategy must be selected") { it.isNotEmpty() }

    val tenants by option("--tenants", "-t", help = "Number of tenants to generate")
        .int()
        .default(DEFAULT_TENANT_COUNT)
        .check("must be greater than 0") { it > 0 }

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

                BenchmarkContext(
                    database = database,
                    strategy = strategy,
                    outputPath = output,
                    verbose = verbose,
                    hashPartitionCount = hashPartitionCount,
                    tenantCount = tenants
                ).use {
                    BenchmarkRunner(it).run()
                }
            } else {
                logger.info { "Skipping benchmark" }
            }
        }
    }

    companion object {
        val databaseChoicesByKey = BenchmarkRunner.databases.associateBy { it.key }
        val strategyChoicesByKey = BenchmarkRunner.strategies.associateBy { it.key }
    }
}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        Benchmark().main(args)
    }
}
