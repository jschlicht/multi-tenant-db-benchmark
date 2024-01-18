package com.github.jschlicht.multitenantdbbenchmark.table

abstract class Table {
}

abstract class GlobalTable : Table()
abstract class TenantTable : Table()