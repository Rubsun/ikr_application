package com.telegin.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val name: String,
    val main: MainDto,
    val weather: List<WeatherInfoDto>,
    val wind: WindDto
)

@Serializable
data class MainDto(
    val temp: Double,
    val humidity: Int
)

@Serializable
data class WeatherInfoDto(
    val description: String,
    val icon: String
)

@Serializable
data class WindDto(
    val speed: Double
)

