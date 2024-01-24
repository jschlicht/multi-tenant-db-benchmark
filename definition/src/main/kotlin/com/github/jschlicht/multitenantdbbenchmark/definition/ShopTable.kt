package com.github.jschlicht.multitenantdbbenchmark.definition

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.CitusTableType
import org.jooq.Query
import org.jooq.impl.DSL.*
import org.jooq.impl.SQLDataType

object ShopTable : GlobalTable {
    override val name = "shop"
    override val citusTableType = CitusTableType.Distributed
    override val distributionColumn = "id"

    override fun definition(ctx: BenchmarkContext, schema: String): Query {
        return ctx.run {
            dsl.createTable(database.qualify(schema, name))
                .column("id", SQLDataType.BIGINT.identity(true))
                .column("address1", SQLDataType.VARCHAR.notNull())
                .column("address2", SQLDataType.VARCHAR.null_())
                .column("city", SQLDataType.VARCHAR.notNull())
                .column("country_code", SQLDataType.VARCHAR.notNull())
                .column("created_at", SQLDataType.LOCALDATETIME.notNull().default_(currentLocalDateTime()))
                .column("customer_email", SQLDataType.VARCHAR.notNull())
                .column("currency", SQLDataType.VARCHAR.notNull())
                .column("domain", SQLDataType.VARCHAR.notNull())
                .column("email", SQLDataType.VARCHAR.notNull())
                .column("name", SQLDataType.VARCHAR.notNull())
                .column("phone", SQLDataType.VARCHAR.null_())
                .column("province", SQLDataType.VARCHAR.notNull())
                .column("shop_owner", SQLDataType.VARCHAR.notNull())
                .column("timezone", SQLDataType.VARCHAR.notNull())
                .column("updated_at", SQLDataType.LOCALDATETIME.notNull().default_(currentLocalDateTime()))
                .column("zip", SQLDataType.VARCHAR.notNull())
                .primaryKey("id")
        }
    }
}
