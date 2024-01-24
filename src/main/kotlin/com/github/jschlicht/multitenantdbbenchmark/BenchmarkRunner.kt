package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
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
        val globalData = dataGenerator.globalData()

        val shopIds = globalData.shops.map { it.id!! }

        definitionGenerator.run(shopIds)

        val dataWriter = DataWriter(this)
        dataWriter.writeGlobal(globalData)
    }
}