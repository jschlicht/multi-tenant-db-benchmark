package com.github.jschlicht.multitenantdbbenchmark.db

import org.jooq.SQLDialect
import org.jooq.util.mysql.MySQLDataType
import org.testcontainers.containers.MySQLContainer

data object MySQL : MySQLBase("mysql", SQLDialect.MYSQL, "main") {
    override fun createContainer(): MySQLContainer<*> {
        return MySQLContainer("mysql")
            .withDatabaseName(defaultSchema)
            .withUrlParam("serverTimezone", "Etc/UTC")
    }
}
