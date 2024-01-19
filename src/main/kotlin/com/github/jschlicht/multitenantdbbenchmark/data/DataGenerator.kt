package com.github.jschlicht.multitenantdbbenchmark.data

import net.datafaker.Faker
import java.util.Random

data class GlobalData(
    val shops: List<Shop>
)

class DataGenerator {
    private val faker = Faker(Random(DETERMINISTIC_SEED))
    private var shopCount = 0L

    fun globalData() : GlobalData {
        return GlobalData(
            shops = shops(SHOP_COUNT)
        )
    }

    fun shops(count: Int) : List<Shop> {
        return (1..count).map {
            Shop(
                id = ++shopCount,
            )
        }
    }

    companion object {
        private const val DETERMINISTIC_SEED = 0L
        private const val SHOP_COUNT = 5
    }
}