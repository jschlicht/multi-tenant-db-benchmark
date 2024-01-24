package com.github.jschlicht.multitenantdbbenchmark.core.db

import org.jooq.SQLDialect
import org.jooq.impl.DSL.collation
import org.jooq.impl.SQLDataType

abstract class MySQLBase(key: String, dialect: SQLDialect, defaultSchema: String) :
    Database(key, dialect, defaultSchema)
