package com.github.jschlicht.multitenantdbbenchmark.strategy

data object Normalized : Strategy("normalized") {
    override val simpleKeys = true
    override val denormalizedTenantId = false
}
