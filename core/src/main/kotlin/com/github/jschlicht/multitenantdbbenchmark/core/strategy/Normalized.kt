package com.github.jschlicht.multitenantdbbenchmark.core.strategy

data object Normalized : Strategy("normalized") {
    override val simpleKeys = true
    override val denormalizedTenantId = false
}
