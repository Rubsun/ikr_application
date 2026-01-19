package com.telegin.impl.domain

import com.telegin.api.domain.models.Weather
import com.telegin.api.domain.usecases.GetWeatherUseCase
import com.telegin.impl.data.WeatherRepository
import com.telegin.network.api.models.WeatherDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

internal class GetWeatherUseCaseImpl(
    private val repository: WeatherRepository
) : GetWeatherUseCase {
    override suspend fun invoke(city: String): Result<Weather> = withContext(Dispatchers.IO) {
        if (city.isBlank()) {
            // Если город пустой, пытаемся загрузить из кэша
            val cached = repository.getCachedWeather()
            return@withContext if (cached != null) {
                success(mapCachedWeather(cached))
            } else {
                failure(IllegalArgumentException("City name cannot be empty"))
            }
        }

        runCatching {
            val dto = repository.getWeather(city)
            mapWeather(dto)
        }
    }
    
    private fun mapCachedWeather(cached: com.telegin.storage.api.models.CachedWeather): Weather {
        return Weather(
            city = cached.city,
            temperature = cached.temperature,
            description = cached.description,
            humidity = cached.humidity,
            windSpeed = cached.windSpeed,
            iconUrl = cached.iconUrl
        )
    }

    private fun mapWeather(dto: WeatherDto): Weather {
        val weatherInfo = dto.weather.firstOrNull()
        val iconUrl = weatherInfo?.icon?.let { icon ->
            "https://openweathermap.org/img/wn/$icon@2x.png"
        }

        return Weather(
            city = dto.name,
            temperature = dto.main.temp,
            description = weatherInfo?.description?.replaceFirstChar { it.uppercase() } ?: "Unknown",
            humidity = dto.main.humidity,
            windSpeed = dto.wind.speed,
            iconUrl = iconUrl
        )
    }
}

