package com.denisova.impl.data

import com.denisova.impl.data.models.GeocodingSearchResponseDto
import com.denisova.impl.data.models.WeatherForecastDto
import com.denisova.impl.data.models.WeatherLocationDto
import com.denisova.impl.data.storage.DenisovaStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

internal class WeatherRepository(
    private val forecastApi: WeatherApiService,
    private val geocodingApi: GeocodingApiService,
    private val storage: DenisovaStorage,
) {
    private val _locations: MutableStateFlow<List<WeatherLocationDto>> = MutableStateFlow(
        storage.readLocations().ifEmpty {
            listOf(
                WeatherLocationDto(
                    id = 1,
                    name = "Sochi",
                    latitude = 43.585472,
                    longitude = 39.723098,
                    country = "Russia",
                    admin1 = "Krasnodarskiy Kray",
                    time = "",
                    temperatureC = Double.NaN,
                    hourlyTimes = emptyList(),
                    hourlyTemperaturesC = emptyList(),
                )
            )
        }
    )

    val locations: StateFlow<List<WeatherLocationDto>> = _locations

    suspend fun refreshAll() {
        val current = _locations.value
        val updated = withContext(Dispatchers.IO) {
            current.map { location ->
                val forecast = forecastApi.getForecast(
                    latitude = location.latitude,
                    longitude = location.longitude,
                )
                val snapshot = forecast.toSnapshot()
                location.copy(
                    time = snapshot.time,
                    temperatureC = snapshot.temperatureC,
                    hourlyTimes = snapshot.hourlyTimes,
                    hourlyTemperaturesC = snapshot.hourlyTemperaturesC,
                )
            }
        }
        _locations.value = updated
        storage.saveLocations(updated)
    }

    suspend fun addLocation(cityName: String): WeatherLocationDto {
        val name = cityName.trim()
        require(name.isNotBlank()) { "City name is blank" }

        val id = (_locations.value.maxOfOrNull { it.id } ?: 0) + 1

        val geocodingResponse = withContext(Dispatchers.IO) {
            geocodingApi.searchCity(name = name)
        }
        val geocodingResult = geocodingResponse.results?.firstOrNull()
            ?: throw IllegalStateException("City not found")

        val forecast = withContext(Dispatchers.IO) {
            forecastApi.getForecast(
                latitude = geocodingResult.latitude,
                longitude = geocodingResult.longitude,
            )
        }
        val snapshot = forecast.toSnapshot()

        val created = WeatherLocationDto(
            id = id,
            name = geocodingResult.name,
            latitude = geocodingResult.latitude,
            longitude = geocodingResult.longitude,
            country = geocodingResult.country,
            admin1 = geocodingResult.admin1,
            time = snapshot.time,
            temperatureC = snapshot.temperatureC,
            hourlyTimes = snapshot.hourlyTimes,
            hourlyTemperaturesC = snapshot.hourlyTemperaturesC,
        )

        val newList = listOf(created) + _locations.value
        _locations.value = newList
        storage.saveLocations(newList)
        return created
    }

    private fun WeatherForecastDto.toSnapshot(): ForecastSnapshot {
        val hourly = hourly
        val hourlyTimes = hourly?.time ?: emptyList()
        val hourlyTemperaturesC = hourly?.temperature2m ?: emptyList()

        val time = hourlyTimes.firstOrNull().orEmpty()
        val temperatureC = hourlyTemperaturesC.firstOrNull() ?: Double.NaN
        return ForecastSnapshot(
            time = time,
            temperatureC = temperatureC,
            hourlyTimes = hourlyTimes,
            hourlyTemperaturesC = hourlyTemperaturesC,
        )
    }

    private data class ForecastSnapshot(
        val time: String,
        val temperatureC: Double,
        val hourlyTimes: List<String>,
        val hourlyTemperaturesC: List<Double>,
    )
}
