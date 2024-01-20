package com.github.jschlicht.multitenantdbbenchmark.db

import com.github.jschlicht.multitenantdbbenchmark.util.PostgresDataType
import org.jooq.DSLContext
import org.jooq.DataType
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.OffsetDateTime

abstract class PostgresBase(key: String)
    : Database(key = key, dialect = SQLDialect.POSTGRES, defaultSchema = "public") {
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
