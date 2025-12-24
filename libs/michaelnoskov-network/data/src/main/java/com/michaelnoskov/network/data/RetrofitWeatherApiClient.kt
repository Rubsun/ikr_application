package com.michaelnoskov.network.data

import com.michaelnoskov.network.api.WeatherApiClient

/**
 * Реализация WeatherApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitWeatherApiClient(
    private val service: WeatherService
) : WeatherApiClient {
    override suspend fun getTemperature(latitude: Double, longitude: Double): Result<Double> {
        return try {
            val response = service.getWeather(latitude, longitude)
            Result.success(response.current.temperature_2m)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

