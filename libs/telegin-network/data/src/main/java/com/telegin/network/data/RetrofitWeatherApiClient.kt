package com.telegin.network.data

import com.telegin.network.api.WeatherApiClient
import com.telegin.network.api.models.WeatherDto

/**
 * Реализация WeatherApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitWeatherApiClient(
    private val service: WeatherService,
    private val apiKey: String
) : WeatherApiClient {
    override suspend fun getWeather(city: String): WeatherDto {
        return service.getWeather(city, apiKey)
    }
}

