package com.telegin.network.data

import com.telegin.network.api.models.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit интерфейс для работы с OpenWeatherMap API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherDto
}

