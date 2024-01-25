package com.github.jschlicht.multitenantdbbenchmark.data

import com.github.jschlicht.multitenantdbbenchmark.data.DataGenerator
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class DataGeneratorTest {
    @Test
    fun `test generated data is deterministic`() {
        val dataGenerator1 = DataGenerator()
        val dataGenerator2 = DataGenerator()

        val shopCount = 5

        val globalData1 = dataGenerator1.globalData(shopCount)
        val globalData2 = dataGenerator2.globalData(shopCount)

        globalData1.shouldBe(globalData2)
    }
}