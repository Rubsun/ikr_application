package com.michaelnoskov.impl.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponse(
    val current: CurrentWeather
)

@Serializable
internal data class CurrentWeather(
    @SerialName("temperature_2m")
    val temperature2m: Double
)

