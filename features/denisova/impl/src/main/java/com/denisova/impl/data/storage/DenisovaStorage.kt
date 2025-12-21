package com.denisova.impl.data.storage

import android.content.SharedPreferences
import com.denisova.impl.data.models.WeatherLocationDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class DenisovaStorage(
    private val prefs: SharedPreferences,
    private val json: Json,
) {
    fun saveLocations(items: List<WeatherLocationDto>) {
        val cached = items.map { it.toCached() }
        prefs.edit().putString(KEY_ITEMS, json.encodeToString(cached)).apply()
    }

    fun readLocations(): List<WeatherLocationDto> {
        val str = prefs.getString(KEY_ITEMS, null) ?: return emptyList()
        return runCatching { json.decodeFromString<List<CachedLocation>>(str) }
            .getOrNull()
            ?.map { it.toDto() }
            ?: emptyList()
    }

    @Serializable
    private data class CachedLocation(
        val id: Int,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val country: String? = null,
        val admin1: String? = null,
        val time: String,
        val temperatureC: Double,
        val hourlyTimes: List<String>,
        val hourlyTemperaturesC: List<Double>,
    )

    private fun WeatherLocationDto.toCached(): CachedLocation = CachedLocation(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        admin1 = admin1,
        time = time,
        temperatureC = temperatureC,
        hourlyTimes = hourlyTimes,
        hourlyTemperaturesC = hourlyTemperaturesC,
    )

    private fun CachedLocation.toDto(): WeatherLocationDto = WeatherLocationDto(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        admin1 = admin1,
        time = time,
        temperatureC = temperatureC,
        hourlyTimes = hourlyTimes,
        hourlyTemperaturesC = hourlyTemperaturesC,
    )

    private companion object {
        const val KEY_ITEMS = "items"
    }
}
