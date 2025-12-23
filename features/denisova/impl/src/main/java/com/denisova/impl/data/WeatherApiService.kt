package com.denisova.impl.data

import com.denisova.impl.data.models.WeatherForecastDto

internal interface WeatherApiService {
    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        hourly: String = "temperature_2m",
        forecastDays: Int = 1,
        timezone: String = "auto",
    ): WeatherForecastDto
}
