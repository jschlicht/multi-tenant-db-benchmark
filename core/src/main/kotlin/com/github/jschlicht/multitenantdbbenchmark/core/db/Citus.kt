package com.github.jschlicht.multitenantdbbenchmark.core.db

import com.github.jschlicht.multitenantdbbenchmark.core.strategy.DistributedTable
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import org.jooq.DSLContext
import org.testcontainers.utility.DockerImageName

enum class CitusTableType {
    Distributed,
    Reference
}

data object Citus : PostgresBase("citus") {
    override val dockerImageName: DockerImageName = DockerImageName.parse(
        "citusdata/citus"
    ).asCompatibleSubstituteFor("postgres")

    override fun setup(dsl: DSLContext) {
        super.setup(dsl)
        dsl.execute("CREATE EXTENSION IF NOT EXISTS citus")
    }

    override fun supports(strategy: Strategy): Boolean {
        return strategy is DistributedTable
    }
}
