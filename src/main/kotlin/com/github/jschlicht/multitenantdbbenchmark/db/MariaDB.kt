package com.github.jschlicht.multitenantdbbenchmark.db

import org.testcontainers.containers.MariaDBContainer

data object MariaDB : MySQLBase("mariadb", "mariadb") {
    override fun createContainer(): MariaDBContainer<*> {
        return MariaDBContainer(containerName)
    }
}