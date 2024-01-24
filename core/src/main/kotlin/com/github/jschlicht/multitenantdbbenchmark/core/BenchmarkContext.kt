package com.github.jschlicht.multitenantdbbenchmark.core

import com.github.jschlicht.multitenantdbbenchmark.core.db.CitusTableType
import com.github.jschlicht.multitenantdbbenchmark.core.db.Database
import com.github.jschlicht.multitenantdbbenchmark.core.db.Postgres
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.DistributedTable
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import com.github.jschlicht.multitenantdbbenchmark.core.util.AutoCloser
import com.github.jschlicht.multitenantdbbenchmark.core.util.SqlOutputExecutionListener
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import org.jooq.DSLContext
import org.jooq.conf.*
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.testcontainers.containers.JdbcDatabaseContainer
import java.io.PrintWriter
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager

private val logger = KotlinLogging.logger {}

data class BenchmarkContext(
    val database: Database,
    val strategy: Strategy,
    val outputPath: Path?,
    val verbose: Boolean
) : AutoCloseable {
    private val closer = AutoCloser()

    val container: JdbcDatabaseContainer<*>
    val connection: Connection
    val dsl: DSLContext
    val printWriter: PrintWriter?

    init {
        try {
            container = closer.register(database.createContainer())
            container.start()

            connection = closer.register(
                DriverManager.getConnection(
                    container.jdbcUrl,
                    container.username, container.password
                )
            )

            outputPath.let {
                if (it != null) {
                    val filePath = it.resolve("${database.key}-${strategy.key}.sql")
                    printWriter = closer.register(PrintWriter(filePath.toFile()))
                } else {
                    printWriter = null
                }
            }

            dsl = DSL.using(
                DefaultConfiguration().apply {
                    set(connection)
                    set(database.dialect)
                    set(SqlOutputExecutionListener(printWriter, verbose))
                    settings().apply {
                        isRenderFormatted = true
                        renderKeywordCase = RenderKeywordCase.UPPER
                        renderQuotedNames = RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED
                    }
                }
            )
        } catch (ex: Exception) {
            logger.error(ex) { "Error setting up benchmark context" }
            closer.save(ex)
            closer.close()
            throw ex
        }
    }



    override fun close() {
        closer.close()
    }
}