package com.github.jschlicht.multitenantdbbenchmark.definition

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.CitusTableType
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.*
import com.github.jschlicht.multitenantdbbenchmark.core.util.MdcKey
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import org.jooq.conf.ParamType

private val logger = KotlinLogging.logger {}

class DefinitionGenerator(private val ctx: BenchmarkContext) {
    private val globalTables = listOf<GlobalTable>(
        ShopTable
    )

    private val multiTenantTables = listOf<MultiTenantTable>(
        CustomerTable
    )

    fun run(shopIds: List<Long>) = ctx.run {
        logger.info { "Running initial database setup" }
        database.setup(dsl)

        val defaultSchema = database.defaultSchema

        val schemas = createSchemas(shopIds)

        globalTables.forEach { table ->
            withLoggingContext(MdcKey.table to table.name) {
                processTable(table, defaultSchema, shopIds)
            }
        }

        multiTenantTables.forEach { table ->
            withLoggingContext(MdcKey.table to table.name) {
                schemas.forEach { schema ->
                    processTable(table, schema, shopIds)
                }
            }
        }
    }

    private fun processTable(table: DbTable, schema: String, shopIds: List<Long>) = ctx.run {
        createTable(table, schema, shopIds)
        setupDistributedOrReferenceTable(table, schema)
        createPartitions(table, shopIds)
        addConstraints(table, schema)
    }

    private fun createTable(table: DbTable, schema: String, shopIds: List<Long>) = ctx.run {
        logger.info { "Creating table" }

        val originalDefinition = table.definition(this, schema).getSQL(ParamType.INLINED)

        val column = table.distributionColumn

        val newDefinition = if (column != null && table is MultiTenantTable) {
            when (strategy.partitioning) {
                Strategy.Partitioning.None -> originalDefinition
                Strategy.Partitioning.List -> "$originalDefinition ${database.listPartition(column, shopIds)}"
                Strategy.Partitioning.Hash -> "$originalDefinition ${database.hashPartition(
                    column,
                    hashPartitionCount
                )}"
            }
        } else {
            originalDefinition
        }

        dsl.execute(newDefinition)
    }

    private fun addConstraints(table: DbTable, schema: String) = ctx.run {
        logger.info { "Adding constraints" }
        table.constraints(this, schema).forEach { constraint ->
            dsl.execute(constraint.getSQL(ParamType.INLINED))
        }
    }

    private fun createPartitions(table: DbTable, shopIds: List<Long>) = ctx.run {
        if (table !is MultiTenantTable || !database.manualPartitionCreation()) {
            return
        }

        when (strategy) {
            PartitionHash -> {
                logger.info { "Creating $hashPartitionCount partitions for hash-based partitioning strategy" }
                for (i in 0..<hashPartitionCount) {
                    dsl.execute(
                        "CREATE TABLE ${table.name}_$i PARTITION OF ${table.name} FOR VALUES WITH (MODULUS $hashPartitionCount, REMAINDER $i)"
                    )
                }
            }
            PartitionList -> {
                logger.info { "Creating one partition per tenant for list-based partitioning strategy" }
                shopIds.forEach { shopId ->
                    dsl.execute("CREATE TABLE ${table.name}_$shopId PARTITION OF ${table.name} FOR VALUES IN ($shopId)")
                }
            }
            else -> {}
        }
    }

    private fun createSchemas(shopIds: List<Long>): List<String> = ctx.run {
        return if (strategy.namespacePerStore) {
            logger.info { "Generating one namespace/schema per store" }
            shopIds.map { shopNamespace(it) }.onEach { schemaName ->
                dsl.createSchema(schemaName).execute()
            }
        } else {
            listOf(database.defaultSchema)
        }
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

    companion object {
        fun shopNamespace(shopId: Long) = "shop_$shopId"
    }
}
