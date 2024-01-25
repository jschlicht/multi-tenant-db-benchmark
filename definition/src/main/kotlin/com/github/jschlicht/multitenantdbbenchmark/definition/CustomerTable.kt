package com.github.jschlicht.multitenantdbbenchmark.definition

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.util.JooqExtensions.multiTenantPrimaryKey
import com.github.jschlicht.multitenantdbbenchmark.core.util.JooqExtensions.optionalIdIndex
import org.jooq.Query
import org.jooq.impl.DSL
import org.jooq.impl.DSL.*
import org.jooq.impl.SQLDataType

object CustomerTable : MultiTenantTable {
    override val name = "customer"

    override fun definition(ctx: BenchmarkContext, schema: String): Query = ctx.run {
        return dsl.createTable(database.qualify(schema, name))
            .column("id", SQLDataType.BIGINT.identity(true))
            .column("shop_id", SQLDataType.BIGINT.notNull())
            .column("email", SQLDataType.VARCHAR.notNull())
            .column("first_name", SQLDataType.VARCHAR.notNull())
            .column("last_name", SQLDataType.VARCHAR.notNull())
            .column("created_at", SQLDataType.LOCALDATETIME.notNull().default_(DSL.currentLocalDateTime()))
            .column("updated_at", SQLDataType.LOCALDATETIME.notNull().default_(DSL.currentLocalDateTime()))
            .column("zip", SQLDataType.VARCHAR.notNull())
            .optionalIdIndex(database, strategy, schema, name)
            .multiTenantPrimaryKey(strategy)
    }

    override fun constraints(ctx: BenchmarkContext, schema: String): List<Query> = ctx.run {
        return listOfNotNull(
            dsl.alterTable(database.qualify(schema, name)).add(
                constraint(database.qualify(schema, "fk_customer_shop"))
                    .foreignKey("shop_id")
                    .references(name("shop"), name("id"))
                    .onDeleteCascade()
            ).takeIf { database.supportsGlobalTableForeignKeysWith(strategy) }
        )
    }
}
