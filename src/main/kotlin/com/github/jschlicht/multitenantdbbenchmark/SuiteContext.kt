package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.core.db.Database
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.Strategy

data class SuiteContext(val databases: List<Database>, val strategies: List<Strategy>)
