package com.github.jschlicht.multitenantdbbenchmark

import com.github.jschlicht.multitenantdbbenchmark.db.Database
import com.github.jschlicht.multitenantdbbenchmark.strategy.Strategy

data class SuiteContext(val databases: List<Database>, val strategies: List<Strategy>)
