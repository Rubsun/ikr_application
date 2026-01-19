package com.telegin.api.ui.models

data class WeatherDisplayModel(
    val city: String,
    val temperature: String,
    val description: String,
    val humidity: String,
    val windSpeed: String,
    val iconUrl: String?
)

