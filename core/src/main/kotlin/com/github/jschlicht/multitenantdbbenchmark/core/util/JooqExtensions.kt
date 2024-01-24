package com.github.jschlicht.multitenantdbbenchmark.core.util

import com.github.jschlicht.multitenantdbbenchmark.core.db.Database
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import org.jooq.CreateTableElementListStep
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType

object JooqExtensions {
    fun CreateTableElementListStep.multiTenantPrimaryKey(
        strategy: Strategy,
        column: String = "id"
    ) : CreateTableElementListStep {
        return if (strategy.simpleKeys) {
            primaryKey(column)
        } else {
            primaryKey("shop_id", column)
        }
    }

    fun CreateTableElementListStep.optionalIdIndex(database: Database, strategy: Strategy, schema: String, table: String) : CreateTableElementListStep {
        if (!database.requiresSeparateIndexOnId(strategy)) {
            return this
        }

        val indexName = database.qualify(schema, "index_${table}_id")
        return index(Internal.createIndex(indexName, table(table), arrayOf(field("id")), false))
    }

    fun CreateTableElementListStep.optionalDenormalizedTenantId(strategy: Strategy) : CreateTableElementListStep {
        return if (strategy.denormalizedTenantId) {
            column("shop_id", SQLDataType.BIGINT.notNull())
        } else {
            this
        }
    }
}