package com.github.jschlicht.multitenantdbbenchmark.core.strategy

sealed class Strategy(val key: String) {
    abstract val simpleKeys: Boolean
    abstract val denormalizedTenantId: Boolean
}
