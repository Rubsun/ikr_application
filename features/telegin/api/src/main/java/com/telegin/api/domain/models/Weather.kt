package com.telegin.api.domain.models

data class Weather(
    val city: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val iconUrl: String?
)

