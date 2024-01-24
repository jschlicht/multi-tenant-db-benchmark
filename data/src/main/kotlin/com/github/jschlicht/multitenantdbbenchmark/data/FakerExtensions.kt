package com.github.jschlicht.multitenantdbbenchmark.data

import net.datafaker.providers.base.DateAndTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Date

object FakerExtensions {
    private const val TEN_YEARS_IN_DAYS = 10L * 365L

    private val maxInstant = Instant.parse("2024-01-01T00:00:00.00Z")
    private val maxDate = Date.from(maxInstant)

    private val minInstant = maxInstant.minus(TEN_YEARS_IN_DAYS, ChronoUnit.DAYS)
    private val minDate = Date.from(minInstant)

    fun DateAndTime.rangeFromPastToPresent(): Pair<Instant, Instant> {
        val firstDate = between(
            minDate,
            maxDate
        )
        val secondDate = between(firstDate, maxDate)

        return firstDate.toInstant() to secondDate.toInstant()
    }

    fun DateAndTime.localRangeFromPastToPresent(): Pair<LocalDateTime, LocalDateTime> {
        val firstDate = between(
            minDate,
            maxDate
        )
        val secondDate = between(firstDate, maxDate)

        return LocalDateTime.ofInstant(firstDate.toInstant(), ZoneOffset.UTC) to
            LocalDateTime.ofInstant(secondDate.toInstant(), ZoneOffset.UTC)
    }
}
