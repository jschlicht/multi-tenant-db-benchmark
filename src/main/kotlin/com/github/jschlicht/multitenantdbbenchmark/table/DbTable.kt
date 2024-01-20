package com.github.jschlicht.multitenantdbbenchmark.table

import com.github.jschlicht.multitenantdbbenchmark.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.data.GlobalData
import com.github.jschlicht.multitenantdbbenchmark.db.CitusTableType
import org.jooq.Query

interface DbTable {
    open val distributionColumn: String?
        get() = null
    val citusTableType: CitusTableType
    val name: String
    fun definition(ctx: BenchmarkContext, schema: String): Query
    fun globalData(ctx: BenchmarkContext, schema: String, globalData: GlobalData): Query?
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

    override fun globalData(ctx: BenchmarkContext, schema: String, globalData: GlobalData): Query? {
        return null
    }
}
