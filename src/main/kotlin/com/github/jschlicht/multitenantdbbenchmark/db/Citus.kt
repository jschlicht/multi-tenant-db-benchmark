package com.github.jschlicht.multitenantdbbenchmark.db

import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy
import org.testcontainers.utility.DockerImageName

data object Citus : PostgresBase("citus") {
    override val dockerImageName: DockerImageName = DockerImageName.parse(
        "citusdata/citus"
    ).asCompatibleSubstituteFor("postgres")

    override fun supports(strategy: Strategy): Boolean {
        return true
    }
}
