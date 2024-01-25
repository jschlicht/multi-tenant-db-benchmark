package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.Database
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class BenchmarkRunnerTest {
    @ParameterizedTest
    @MethodSource("arguments")
    fun `runs all supported strategies against all supported databases`(database: Database, strategy: Strategy) {
        BenchmarkRunner(
            BenchmarkContext(
                database = database,
                strategy = strategy,
                outputPath = null,
                verbose = false,
                hashPartitionCount = 1,
                tenantCount = 1
            )
        ).run()
    }

    companion object {
        @JvmStatic
        @Suppress("detekt:UnusedPrivateMember")
        private fun arguments(): List<Arguments> {
            return BenchmarkRunner.defaultDatabases.flatMap { database ->
                BenchmarkRunner.strategies.mapNotNull { strategy ->
                    Arguments.of(database, strategy)
                        .takeIf { database.supports(strategy) }
                }
            }
        }
    }
}
