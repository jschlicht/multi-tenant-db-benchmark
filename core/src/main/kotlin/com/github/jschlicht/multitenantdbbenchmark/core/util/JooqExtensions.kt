package com.github.jschlicht.multitenantdbbenchmark.core.util

import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy
import org.jooq.CreateTableElementListStep
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

    fun CreateTableElementListStep.optionalDenormalizedTenantId(strategy: Strategy) : CreateTableElementListStep {
        return if (strategy.denormalizedTenantId) {
            column("shop_id", SQLDataType.BIGINT.notNull())
        } else {
            this
        }
    }
}