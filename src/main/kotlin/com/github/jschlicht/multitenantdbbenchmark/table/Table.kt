package com.github.jschlicht.multitenantdbbenchmark.table

import com.github.jschlicht.multitenantdbbenchmark.BenchmarkContext
import org.jooq.Query
import org.jooq.Statement

interface Table {
    val name: String
    fun definition(ctx: BenchmarkContext, schema: String) : List<Query>
}

interface GlobalTable : Table
interface MultiTenantTable : Table
