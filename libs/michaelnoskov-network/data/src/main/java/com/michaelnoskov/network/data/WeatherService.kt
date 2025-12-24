package com.michaelnoskov.network.data

import com.michaelnoskov.network.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit интерфейс для работы с Open-Meteo API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface WeatherService {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m"
    ): WeatherResponse
}

