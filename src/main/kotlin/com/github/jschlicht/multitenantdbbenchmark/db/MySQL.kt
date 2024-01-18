package com.github.jschlicht.multitenantdbbenchmark.db

import org.testcontainers.containers.MySQLContainer

data object MySQL : MySQLBase("mysql") {
    override fun createContainer(): MySQLContainer<*> {
        return MySQLContainer("mysql")
    }
}
