package com.github.jschlicht.multitenantdbbenchmark.db

import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy
import org.jooq.DSLContext
import org.testcontainers.utility.DockerImageName

data object Citus : PostgresBase("citus") {
    override val dockerImageName: DockerImageName = DockerImageName.parse(
        "citusdata/citus"
    ).asCompatibleSubstituteFor("postgres")

    override fun setup(dsl: DSLContext) {
        super.setup(dsl)
        dsl.execute("CREATE EXTENSION IF NOT EXISTS citus")
    }

    override fun supports(strategy: Strategy): Boolean {
        return true
    }
}
