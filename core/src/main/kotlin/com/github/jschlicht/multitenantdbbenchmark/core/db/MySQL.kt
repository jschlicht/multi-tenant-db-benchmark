package com.github.jschlicht.multitenantdbbenchmark.core.db

import org.jooq.SQLDialect
import org.testcontainers.containers.MySQLContainer
import java.time.Duration

data object MySQL : MySQLBase("mysql", SQLDialect.MYSQL, "main") {
    override fun createContainer(): MySQLContainer<*> {
        return MySQLContainer("mysql")
            .withDatabaseName(defaultSchema)
            .withConnectTimeoutSeconds(CONNECT_TIMEOUT_SECONDS)
            .withUrlParam("serverTimezone", "Etc/UTC")
    }

    // MySQL can be slow to start up on github actions, so give it more time.
    private const val CONNECT_TIMEOUT_SECONDS = 60*5
}
