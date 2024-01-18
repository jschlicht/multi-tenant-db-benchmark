package com.github.jschlicht.multitenantdbbenchmark.db

import org.testcontainers.containers.MySQLContainer

data object MySQL : MySQLBase("mysql", "mysql") {
    override fun createContainer(): MySQLContainer<*> {
        return MySQLContainer(containerName)
    }
}