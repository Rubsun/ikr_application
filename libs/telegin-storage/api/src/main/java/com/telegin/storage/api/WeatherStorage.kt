package com.telegin.storage.api

import com.telegin.storage.api.models.CachedWeather
import kotlinx.coroutines.flow.Flow

interface WeatherStorage {
    fun getLastCity(): Flow<String>
    
    suspend fun saveLastCity(city: String)
    
    fun getCachedWeather(): Flow<CachedWeather?>
    
    suspend fun saveCachedWeather(weather: CachedWeather)
    
    suspend fun clearCache()
}

