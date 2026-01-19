package com.telegin.storage.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.telegin.storage.api.WeatherStorage
import com.telegin.storage.api.models.CachedWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
internal data class TeleginStorageState(
    val lastCity: String = "",
    val cachedWeather: CachedWeather? = null
)

internal class TeleginStorageImpl(
    private val storage: PrimitiveStorage<TeleginStorageState>
) : WeatherStorage {
    override fun getLastCity(): Flow<String> {
        return storage.get().map { it?.lastCity ?: "" }
    }

    override suspend fun saveLastCity(city: String) {
        storage.patch { state ->
            (state ?: TeleginStorageState()).copy(lastCity = city)
        }
    }

    override fun getCachedWeather(): Flow<CachedWeather?> {
        return storage.get().map { it?.cachedWeather }
    }

    override suspend fun saveCachedWeather(weather: CachedWeather) {
        storage.patch { state ->
            (state ?: TeleginStorageState()).copy(cachedWeather = weather)
        }
    }

    override suspend fun clearCache() {
        storage.patch { state ->
            state?.copy(cachedWeather = null)
        }
    }
}

