package com.github.jschlicht.multitenantdbbenchmark.core.strategy

data object TenantIdComposite : Strategy("tenant_id_composite") {
    override val simpleKeys = false
    override val denormalizedTenantId = true
}
