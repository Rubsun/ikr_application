package com.example.ikr_application.denisova.data

import com.example.ikr_application.denisova.data.models.WeatherForecastDto
import com.example.ikr_application.denisova.data.models.WeatherLocationDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val FORECAST_BASE_URL = "https://api.open-meteo.com/"
private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"

object WeatherRepository {
    private val forecastApi by lazy { createForecastService() }
    private val geocodingApi by lazy { createGeocodingService() }

    private val json = Json { ignoreUnknownKeys = true }
    private val okHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val _locations = MutableStateFlow(
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

        _locations.value = listOf(created) + _locations.value
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

    private fun createForecastService(): WeatherApiService {
        return createRetrofit(FORECAST_BASE_URL)
            .create(WeatherApiService::class.java)
    }

    private fun createGeocodingService(): GeocodingApiService {
        return createRetrofit(GEOCODING_BASE_URL)
            .create(GeocodingApiService::class.java)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
