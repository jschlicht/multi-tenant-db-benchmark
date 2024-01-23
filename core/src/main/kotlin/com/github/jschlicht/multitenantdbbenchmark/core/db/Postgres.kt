package com.github.jschlicht.multitenantdbbenchmark.core.db

import org.testcontainers.utility.DockerImageName

data object Postgres : PostgresBase("postgres") {
    override val dockerImageName: DockerImageName = DockerImageName.parse("postgres")
}
