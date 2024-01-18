package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.db.Database
import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy
import com.github.jschlicht.multitenantdbbenchmark.table.ShopTable
import io.github.oshai.kotlinlogging.KotlinLogging
import java.sql.DriverManager

private val logger = KotlinLogging.logger {}

data class BenchmarkContext(val database: Database, val strategy: Strategy) {
    private val tables = listOf(
        ShopTable()
    )

    fun run() {
        database.createContainer().use { container ->
            container.start()
            DriverManager.getConnection(container.jdbcUrl, container.username, container.password).use { connection ->
                logger.info { "Got connection for ${container.jdbcUrl}" }
            }
        }
    }
}
