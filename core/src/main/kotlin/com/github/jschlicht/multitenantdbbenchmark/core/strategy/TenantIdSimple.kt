package com.github.jschlicht.multitenantdbbenchmark.core.strategy

data object TenantIdSimple : Strategy("tenant_id_simple") {
    override val simpleKeys = true
    override val denormalizedTenantId = true
}
