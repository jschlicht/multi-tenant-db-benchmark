package com.github.jschlicht.multitenantdbbenchmark.db

import org.jooq.SQLDialect

abstract class MySQLBase(key: String, dialect: SQLDialect, defaultSchema: String)
    : Database(key, dialect, defaultSchema)
