package com.github.jschlicht.multitenantdbbenchmark.table

import com.github.jschlicht.multitenantdbbenchmark.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.data.GlobalData
import com.github.jschlicht.multitenantdbbenchmark.db.CitusTableType
import org.jooq.Query
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row
import org.jooq.RowN
import org.jooq.impl.DSL.*
import org.jooq.impl.SQLDataType

object ShopTable : GlobalTable {
    override val name = "shops"
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
                .column("customer_email", database.caseInsensitiveType.notNull())
                .column("currency", SQLDataType.VARCHAR.notNull())
                .column("domain", SQLDataType.VARCHAR.notNull())
                .column("email", database.caseInsensitiveType.notNull())
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

    override fun globalData(ctx: BenchmarkContext, schema: String, globalData: GlobalData): Query {
        return ctx.run {
            val rows : List<RowN> = globalData.shops.map { shop ->
                row(listOf(
                    shop.id, shop.address1, shop.address2, shop.city, shop.countryCode, shop.createdAt,
                    shop.customerEmail, shop.currency, shop.domain, shop.email, shop.name, shop.phone,
                    shop.province, shop.shopOwner, shop.timezone, shop.updatedAt, shop.zip
                ))
            }

            dsl.insertInto(table(database.qualify(schema, name)))
                .columns(
                    listOf(
                        "id", "address1", "address2", "city", "country_code", "created_at",
                        "customer_email", "currency", "domain", "email", "name", "phone",
                        "province", "shop_owner", "timezone", "updated_at", "zip"
                    ).map { field(it) }
                ).valuesOfRows(rows)
        }
    }
}
