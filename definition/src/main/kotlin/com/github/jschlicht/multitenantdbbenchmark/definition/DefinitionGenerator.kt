package com.github.jschlicht.multitenantdbbenchmark.definition

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.CitusTableType
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.DistributedTable
import com.github.jschlicht.multitenantdbbenchmark.core.util.MdcKey
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext

private val logger = KotlinLogging.logger {}

class DefinitionGenerator(private val ctx: BenchmarkContext) {
    private val globalTables = listOf<GlobalTable>(
        ShopTable
    )

    fun run(shopIds: List<Long>) = ctx.run {
        logger.info { "Running initial database setup" }
        database.setup(dsl)

        val defaultSchema = database.defaultSchema

        globalTables.forEach { table ->
            withLoggingContext(MdcKey.table to table.name) {
                createTable(table, database.defaultSchema)
                setupDistributedOrReferenceTable(table, defaultSchema)
            }
        }
    }

    private fun createTable(table: DbTable, schema: String) = ctx.run {
        logger.info { "Creating table" }
        dsl.execute(table.definition(this, schema))
    }

    private fun setupDistributedOrReferenceTable(table: DbTable, schema: String): Unit = ctx.run {
        if (strategy !is DistributedTable) {
            return
        }

        val tableName = database.qualify(schema, table.name).unquotedName().toString()

        when (table.citusTableType) {
            CitusTableType.Distributed -> {
                table.distributionColumn?.let { distributionColumn ->
                    logger.info { "Creating distributed table" }
                    dsl.execute("SELECT create_distributed_table(?, ?)", tableName, distributionColumn)
                }
            }
            CitusTableType.Reference -> {
                logger.info { "Creating reference table" }
                dsl.execute("SELECT create_reference_table(?)", tableName)
            }
        }
    }
}