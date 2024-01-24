package com.github.jschlicht.multitenantdbbenchmark.core.strategy

data object Namespace : Strategy("namespace") {
    override val simpleKeys = true
    override val denormalizedTenantId = false
    override val namespacePerStore = true
}
