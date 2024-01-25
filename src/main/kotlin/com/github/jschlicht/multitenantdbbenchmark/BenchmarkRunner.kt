package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.Citus
import com.github.jschlicht.multitenantdbbenchmark.core.db.MariaDB
import com.github.jschlicht.multitenantdbbenchmark.core.db.MySQL
import com.github.jschlicht.multitenantdbbenchmark.core.db.Postgres
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.*
import com.github.jschlicht.multitenantdbbenchmark.data.DataGenerator
import com.github.jschlicht.multitenantdbbenchmark.data.DataWriter
import com.github.jschlicht.multitenantdbbenchmark.definition.DefinitionGenerator
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class BenchmarkRunner(private val ctx: BenchmarkContext) {
    private val definitionGenerator = DefinitionGenerator(ctx)

    fun run() = ctx.run {
        logger.info { "Generating data" }
        val dataGenerator = DataGenerator()
        val globalData = dataGenerator.globalData(shopCount = tenantCount)

        val shopIds = globalData.shops.mapNotNull { it.id }

        definitionGenerator.run(shopIds)

        val dataWriter = DataWriter(this)
        dataWriter.writeGlobal(globalData)
    }

    companion object {
        val defaultDatabases = listOf(MariaDB, Postgres, Citus)
        val databases = defaultDatabases + MySQL
        val strategies = listOf(
            Normalized,
            TenantIdSimple,
            TenantIdComposite,
            PartitionHash,
            PartitionList,
            Namespace,
            DistributedTable
        )
    }
}
