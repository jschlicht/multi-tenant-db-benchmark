package com.github.jschlicht.multitenantdbbenchmark.data

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.model.jooq.tables.Shop.Companion.SHOP
import org.jooq.Field
import org.jooq.Fields
import org.jooq.Record
import org.jooq.conf.ParamType
import org.jooq.impl.DSL.table
import org.jooq.impl.TableImpl

class DataWriter(private val ctx: BenchmarkContext) {
    fun writeGlobal(data: GlobalData) = ctx.run {
        val defaultSchema = database.defaultSchema
        SHOP.insert(defaultSchema, data.shops)
    }

    private fun <E : Record> TableImpl<E>.insert(
        schema: String,
        values: List<Any>,
        alwaysInsertTenantId: Boolean = false
    ) {
        val records = values.map { ctx.dsl.newRecord(this, it) }

        // Prevent jooq from qualifying the default schema name (just insert into shops, not public.shops)
        val tableName = ctx.database.qualify(schema, name)

        val sql = ctx.dsl.insertInto(table(tableName))
            .columns(fieldsHandlingTenantId(alwaysInsertTenantId))
            .valuesOfRecords(records)
            .getSQL(ParamType.INLINED)

        ctx.dsl.execute(sql)
    }

    private fun Fields.fieldsHandlingTenantId(alwaysInsertTenantId: Boolean): List<Field<*>> {
        val allFields = fields()

        return if (alwaysInsertTenantId || ctx.strategy.denormalizedTenantId) {
            allFields.toList()
        } else {
            allFields.filter { it.name != "shop_id" }
        }
    }
}
