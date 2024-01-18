package com.github.jschlicht.multitenantdbbenchmark.strategy

data object PartitionHash : Strategy("partition_hash") {
    override val simpleKeys = false
    override val denormalizedTenantId = true
}
