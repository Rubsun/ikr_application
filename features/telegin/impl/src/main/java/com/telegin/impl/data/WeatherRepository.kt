package com.telegin.impl.data

import com.telegin.network.api.WeatherApiClient
import com.telegin.network.api.models.WeatherDto
import com.telegin.storage.api.WeatherStorage
import com.telegin.storage.api.models.CachedWeather
import kotlinx.coroutines.flow.first

internal class WeatherRepository(
    private val apiClient: WeatherApiClient,
    private val storage: WeatherStorage
) {
    suspend fun getWeather(city: String): WeatherDto {
        val weather = apiClient.getWeather(city)
        
        // Сохраняем в кэш
        storage.saveCachedWeather(
            CachedWeather(
                city = weather.name,
                temperature = weather.main.temp,
                description = weather.weather.firstOrNull()?.description ?: "",
                humidity = weather.main.humidity,
                windSpeed = weather.wind.speed,
                iconUrl = weather.weather.firstOrNull()?.icon?.let { 
                    "https://openweathermap.org/img/wn/$it@2x.png" 
                }
            )
        )
        
        // Сохраняем последний город
        storage.saveLastCity(city)
        
        return weather
    }
    
    suspend fun getCachedWeather(): CachedWeather? {
        return storage.getCachedWeather().first()
    }
    
    suspend fun getLastCity(): String {
        return storage.getLastCity().first()
    }
}

