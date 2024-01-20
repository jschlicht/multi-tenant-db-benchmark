package com.github.jschlicht.multitenantdbbenchmark.strategy

data object PartitionList : Strategy("partition_list") {
    override val simpleKeys = false
    override val denormalizedTenantId = true
}