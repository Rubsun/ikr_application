package com.denisova.impl.data

import com.denisova.impl.data.models.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m",
        @Query("forecast_days") forecastDays: Int = 1,
        @Query("timezone") timezone: String = "auto",
    ): WeatherForecastDto
}
