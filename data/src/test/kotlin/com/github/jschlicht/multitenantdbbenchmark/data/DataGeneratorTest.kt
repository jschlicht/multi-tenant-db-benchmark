package com.github.jschlicht.multitenantdbbenchmark.data

import com.github.jschlicht.multitenantdbbenchmark.data.DataGenerator
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DataGeneratorTest {
    @Test
    fun `test generated data is deterministic`() {
        val dataGenerator1 = DataGenerator()
        val dataGenerator2 = DataGenerator()

        val globalData1 = dataGenerator1.globalData()
        val globalData2 = dataGenerator2.globalData()

        globalData1.shouldBe(globalData2)
    }
}