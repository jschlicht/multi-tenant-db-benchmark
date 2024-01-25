package com.github.jschlicht.multitenantdbbenchmark.model

import org.junit.jupiter.api.Test

class JooqGeneratorTest {
    // also allows us to detect uncommitted jooq changes
    @Test
    fun `generator runs without crashing`() {
        JooqGenerator().run()
    }
}