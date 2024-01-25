package com.github.jschlicht.multitenantdbbenchmark.definition

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.CitusTableType
import org.jooq.Query

interface DbTable {
    open val distributionColumn: String?
        get() = null
    val citusTableType: CitusTableType
    val name: String
    fun definition(ctx: BenchmarkContext, schema: String): Query
    fun constraints(ctx: BenchmarkContext, schema: String): List<Query>
}

interface GlobalTable : DbTable {
    override val citusTableType: CitusTableType
        get() = CitusTableType.Reference
}

interface MultiTenantTable : DbTable {
    override val distributionColumn: String?
        get() = "shop_id"

    override val citusTableType: CitusTableType
        get() = CitusTableType.Distributed
}
