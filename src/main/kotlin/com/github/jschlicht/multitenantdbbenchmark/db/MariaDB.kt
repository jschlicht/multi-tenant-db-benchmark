package com.github.jschlicht.multitenantdbbenchmark.db

import org.jooq.SQLDialect
import org.testcontainers.containers.MariaDBContainer

data object MariaDB : MySQLBase("mariadb", SQLDialect.MARIADB, "main") {
    override fun createContainer(): MariaDBContainer<*> {
        return MariaDBContainer("mariadb")
            .withDatabaseName(defaultSchema)
    }
}
