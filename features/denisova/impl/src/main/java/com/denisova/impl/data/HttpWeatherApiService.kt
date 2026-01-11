package com.denisova.impl.data

import com.denisova.impl.data.models.WeatherForecastDto
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

internal class HttpWeatherApiService(
    private val json: Json,
) : WeatherApiService {

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        hourly: String,
        forecastDays: Int,
        timezone: String,
    ): WeatherForecastDto {
        val baseUrl = "https://api.open-meteo.com/v1/forecast"
        val query = buildString {
            append("latitude=").append(latitude)
            append("&longitude=").append(longitude)
            append("&hourly=").append(URLEncoder.encode(hourly, "UTF-8"))
            append("&forecast_days=").append(forecastDays)
            append("&timezone=").append(URLEncoder.encode(timezone, "UTF-8"))
        }
        val url = URL("$baseUrl?$query")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 10000
            readTimeout = 15000
        }
        connection.inputStream.use { input ->
            val body = input.bufferedReader().use { it.readText() }
            return json.decodeFromString(body)
        }
    }
}
