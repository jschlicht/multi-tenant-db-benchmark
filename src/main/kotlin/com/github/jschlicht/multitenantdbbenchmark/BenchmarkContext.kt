package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.db.Database
import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy
import com.github.jschlicht.multitenantdbbenchmark.table.GlobalTable
import com.github.jschlicht.multitenantdbbenchmark.table.MultiTenantTable
import com.github.jschlicht.multitenantdbbenchmark.table.ShopTable
import com.github.jschlicht.multitenantdbbenchmark.util.AutoCloser
import com.github.jschlicht.multitenantdbbenchmark.util.SqlOutputExecutionListener
import com.google.common.io.Closer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.commons.io.IOUtils
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.RenderKeywordCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.slf4j.MDC
import org.testcontainers.containers.JdbcDatabaseContainer
import java.io.PrintWriter
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager

private val logger = KotlinLogging.logger {}

data class BenchmarkContext(val database: Database, val strategy: Strategy, val outputPath: Path?) : AutoCloseable {
    private val closer = AutoCloser()

    private val globalTables = listOf<GlobalTable>(
        ShopTable
    )

    private val multiTenantTables = listOf<MultiTenantTable>()

    val container: JdbcDatabaseContainer<*>
    val connection: Connection
    val dsl: DSLContext
    val printWriter: PrintWriter?

    init {
        try {
            container = closer.register(database.createContainer())
            container.start()

            connection = closer.register(DriverManager.getConnection(container.jdbcUrl,
                container.username, container.password)
            )

            outputPath.let {
                if (it != null) {
                    val filePath = it.resolve("${database.key}-${strategy.key}.sql")
                    printWriter = closer.register(PrintWriter(filePath.toFile()))
                } else {
                    printWriter = null
                }
            }

            dsl = DSL.using(DefaultConfiguration().apply {
                set(connection)
                set(database.dialect)
                if (printWriter != null) {
                    set(SqlOutputExecutionListener(printWriter))
                }
                settings().apply {
                    isRenderFormatted = true
                    isRenderSchema = true
                    renderKeywordCase = RenderKeywordCase.UPPER
                    renderQuotedNames = RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED
                }
            })

        } catch (ex: Exception) {
            logger.error(ex) { "Error setting up benchmark context" }
            closer.save(ex)
            closer.close()
            throw ex
        }
    }

    fun run() {
        logger.info { "Running initial database setup" }
        database.setup(dsl)

        globalTables.forEach { table ->
            MDC.put("table", table.name)
            logger.info { "Creating table" }
            
            table.definition(this, database.defaultSchema).forEach { statement ->
                logger.debug { statement.sql }
                dsl.execute(statement)
            }
        }
    }

    override fun close() {
        closer.close()
    }
}
