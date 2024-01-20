package com.github.jschlicht.multitenantdbbenchmark.data

import java.time.LocalDateTime

data class Shop(
    val id: Long,
    val address1: String,
    val address2: String?,
    val city: String,
    val countryCode: String,
    val createdAt: LocalDateTime,
    val customerEmail: String,
    val currency: String,
    val domain: String,
    val email: String,
    val name: String,
    val phone: String?,
    val province: String,
    val shopOwner: String,
    val timezone: String,
    val updatedAt: LocalDateTime,
    val zip: String,
)