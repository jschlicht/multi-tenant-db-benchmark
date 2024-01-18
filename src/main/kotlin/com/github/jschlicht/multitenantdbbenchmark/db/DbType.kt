package com.github.jschlicht.multitenantdbbenchmark.db

sealed class DbType(val key: String) {
    data object Citus : DbType("citus")
    data object MariaDb : DbType("mariadb")
    data object Mysql : DbType("mysql")
    data object Postgres : DbType("postgres")
}