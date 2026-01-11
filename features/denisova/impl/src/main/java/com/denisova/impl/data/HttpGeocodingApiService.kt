package com.denisova.impl.data

import com.denisova.impl.data.models.GeocodingSearchResponseDto
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

internal class HttpGeocodingApiService(
    private val json: Json,
) : GeocodingApiService {

    override suspend fun searchCity(
        name: String,
        count: Int,
        language: String,
        format: String,
    ): GeocodingSearchResponseDto {
        val baseUrl = "https://geocoding-api.open-meteo.com/v1/search"
        val query = buildString {
            append("name=").append(URLEncoder.encode(name, "UTF-8"))
            append("&count=").append(count)
            append("&language=").append(URLEncoder.encode(language, "UTF-8"))
            append("&format=").append(URLEncoder.encode(format, "UTF-8"))
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
