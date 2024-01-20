package com.github.jschlicht.multitenantdbbenchmark.db

import org.jooq.SQLDialect
import org.jooq.impl.DSL.collation
import org.jooq.impl.SQLDataType

abstract class MySQLBase(key: String, dialect: SQLDialect, defaultSchema: String) :
    Database(key, dialect, defaultSchema) {
    override val caseInsensitiveType = SQLDataType.VARCHAR.collation(collation("utf8mb4_unicode_ci"))
}
