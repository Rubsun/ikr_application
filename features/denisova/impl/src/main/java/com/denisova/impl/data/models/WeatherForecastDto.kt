package com.denisova.impl.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherForecastDto(
    val latitude: Double,
    val longitude: Double,
    val hourly: WeatherHourlyDto? = null,
)

@Serializable
internal data class WeatherHourlyDto(
    val time: List<String> = emptyList(),
    @SerialName("temperature_2m")
    val temperature2m: List<Double> = emptyList(),
)
