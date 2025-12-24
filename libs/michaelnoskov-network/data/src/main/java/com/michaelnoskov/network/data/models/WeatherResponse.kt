package com.michaelnoskov.network.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponse(
    val current: CurrentWeather
)

@Serializable
internal data class CurrentWeather(
    val temperature_2m: Double
)

