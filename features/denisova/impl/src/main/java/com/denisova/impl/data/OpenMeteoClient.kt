package com.denisova.impl.data

import com.denisova.impl.data.models.GeocodingSearchResponseDto
import com.denisova.impl.data.models.WeatherForecastDto

internal interface OpenMeteoClient {
    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        hourly: String = "temperature_2m",
        forecastDays: Int = 1,
        timezone: String = "auto",
    ): WeatherForecastDto

    suspend fun searchCity(
        name: String,
        count: Int = 1,
        language: String = "en",
        format: String = "json",
    ): GeocodingSearchResponseDto
}
