package com.github.jschlicht.multitenantdbbenchmark.data

import com.github.jschlicht.multitenantdbbenchmark.data.FakerExtensions.localRangeFromPastToPresent
import com.github.jschlicht.multitenantdbbenchmark.model.jooq.tables.pojos.Shop
import net.datafaker.Faker
import java.util.Random

data class GlobalData(
    val shops: List<Shop>
)

class DataGenerator {
    private val faker = Faker(Random(DETERMINISTIC_SEED))
    private var shopCounter = 0L

    fun globalData(shopCount: Int): GlobalData {
        return GlobalData(
            shops = shops(shopCount)
        )
    }

    fun shops(count: Int): List<Shop> {
        return (1..count).map {
            val (createdAt, updatedAt) = faker.date().localRangeFromPastToPresent()

            Shop(
                id = ++shopCounter,
                address1 = faker.address().streetAddress(false),
                address2 = if (faker.random().nextBoolean()) {
                    faker.address().secondaryAddress()
                } else {
                    null
                },
                city = faker.address().city(),
                countryCode = faker.address().countryCode(),
                createdAt = createdAt,
                customerEmail = faker.internet().emailAddress(),
                currency = faker.currency().code(),
                domain = faker.internet().domainName(),
                email = faker.internet().emailAddress(),
                name = faker.company().name(),
                phone = if (faker.random().nextBoolean()) {
                    faker.phoneNumber().phoneNumber()
                } else {
                    null
                },
                province = faker.address().state(),
                shopOwner = faker.name().fullName(),
                timezone = faker.address().timeZone(),
                updatedAt = updatedAt,
                zip = faker.address().zipCode()
            )
        }
    }

    companion object {
        private const val DETERMINISTIC_SEED = 0L
    }
}
