package com.github.jschlicht.multitenantdbbenchmark.strategy

data object Namespace : Strategy("namespace") {
    override val simpleKeys = true
    override val denormalizedTenantId = false
}
