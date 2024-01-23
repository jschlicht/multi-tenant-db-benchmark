package com.github.jschlicht.multitenantdbbenchmark.core.strategy

data object DistributedTable : Strategy("distributed_table") {
    override val simpleKeys = false
    override val denormalizedTenantId = true
}
