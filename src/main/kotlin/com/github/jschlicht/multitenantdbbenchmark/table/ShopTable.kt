package com.github.jschlicht.multitenantdbbenchmark.table

import com.github.jschlicht.multitenantdbbenchmark.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.db.CitusTableType
import org.jooq.Query
import org.jooq.Statement
import org.jooq.impl.DSL.name
import org.jooq.impl.SQLDataType

object ShopTable : GlobalTable {
    override val name = "shops"
    override val citusTableType = CitusTableType.Distributed
    override val distributionColumn = "id"

    override fun definition(ctx: BenchmarkContext, schema: String): List<Query> {
        return ctx.run {
            listOf(
                dsl.createTable(database.qualify(schema, name))
                    .column("id", SQLDataType.BIGINT.identity(true))
                    .primaryKey("id")
            )
        }
    }
}
