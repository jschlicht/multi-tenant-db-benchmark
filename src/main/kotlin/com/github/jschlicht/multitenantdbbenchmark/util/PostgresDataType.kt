package com.github.jschlicht.multitenantdbbenchmark.util

import org.jooq.SQLDialect
import org.jooq.impl.BuiltInDataType
import org.jooq.impl.SQLDataType

object PostgresDataType {
    val CITEXT = BuiltInDataType(SQLDialect.POSTGRES, SQLDataType.VARCHAR, "citext")
}
