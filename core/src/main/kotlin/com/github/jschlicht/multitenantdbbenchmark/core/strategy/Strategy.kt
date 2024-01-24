package com.github.jschlicht.multitenantdbbenchmark.core.strategy

sealed class Strategy(val key: String) {
    enum class Partitioning {
        None,
        List,
        Hash
    }

    abstract val simpleKeys: Boolean
    abstract val denormalizedTenantId: Boolean
    open val partitioning: Partitioning = Partitioning.None
    open val namespacePerStore: Boolean = false
}
