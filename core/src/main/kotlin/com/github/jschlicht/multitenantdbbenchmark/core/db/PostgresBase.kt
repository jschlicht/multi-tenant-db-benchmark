package com.github.jschlicht.multitenantdbbenchmark.core.db

import com.github.jschlicht.multitenantdbbenchmark.core.util.PostgresDataType
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

abstract class PostgresBase(key: String) :
    Database(key = key, dialect = SQLDialect.POSTGRES, defaultSchema = "public") {
    abstract val dockerImageName: DockerImageName
    override val caseInsensitiveType = PostgresDataType.CITEXT

    override fun setup(dsl: DSLContext) {
        dsl.execute("CREATE EXTENSION IF NOT EXISTS citext")
    }

    override fun createContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(dockerImageName)
            .withCommand("postgres") // test-containers disables fsync by default
    }
}