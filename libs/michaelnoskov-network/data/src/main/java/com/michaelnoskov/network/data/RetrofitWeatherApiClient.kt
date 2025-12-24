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
            val temperature = response.current.temperature_2m
            android.util.Log.d("WeatherApi", "Получена температура: $temperature°C для координат ($latitude, $longitude)")
            Result.success(temperature)
        } catch (e: Exception) {
            android.util.Log.e("WeatherApi", "Ошибка получения температуры: ${e.message}", e)
            Result.failure(e)
        }
    }
}

