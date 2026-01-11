package com.fomin.impl.data

import android.util.Log
import com.example.primitivestorage.api.PrimitiveStorage
import com.fomin.api.domain.models.CatBreed
import com.fomin.api.domain.models.CatWeight
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable

private const val TAG = "FominStorage"

internal class FominStorage(
    private val storage: PrimitiveStorage<State>,
) {
    suspend fun getBreed(breedId: String): CatBreed? {
        val state = storage.get().first()
        val cachedBreed = state?.breeds?.get(breedId)?.toDomain()
        
        if (cachedBreed != null) {
            Log.d(TAG, "📖 Чтение из кеша: breedId=$breedId, имя=${cachedBreed.name}, всего в кеше: ${state?.breeds?.size ?: 0}")
        } else {
            Log.d(TAG, "📭 Кеш пуст для breedId=$breedId, всего в кеше: ${state?.breeds?.size ?: 0}")
        }
        
        return cachedBreed
    }

    suspend fun saveBreed(breed: CatBreed) {
        storage.patch { old ->
            val breeds = (old?.breeds ?: emptyMap()).toMutableMap()
            breeds[breed.id] = breed.toCached()
            val newState = (old ?: State()).copy(breeds = breeds)
            
            Log.d(TAG, "💾 Сохранение в кеш: breedId=${breed.id}, имя=${breed.name}, всего в кеше: ${breeds.size}")
            
            newState
        }
    }

    suspend fun hasBreed(breedId: String): Boolean {
        val state = storage.get().first()
        val hasBreed = state?.breeds?.containsKey(breedId) == true
        Log.d(TAG, "🔎 Проверка наличия в кеше: breedId=$breedId, результат=$hasBreed")
        return hasBreed
    }

    @Serializable
    internal data class State(
        val breeds: Map<String, CachedBreed> = emptyMap(),
    )

    @Serializable
    internal data class CachedBreed(
        val id: String,
        val name: String,
        val description: String?,
        val temperament: String?,
        val origin: String?,
        val lifeSpan: String?,
        val weight: CachedWeight?,
        val imageUrl: String?,
    )

    @Serializable
    internal data class CachedWeight(
        val imperial: String?,
        val metric: String?,
    )

    private fun CachedBreed.toDomain(): CatBreed {
        return CatBreed(
            id = id,
            name = name,
            description = description,
            temperament = temperament,
            origin = origin,
            lifeSpan = lifeSpan,
            weight = weight?.toDomain(),
            imageUrl = imageUrl,
        )
    }

    private fun CatBreed.toCached(): CachedBreed {
        return CachedBreed(
            id = id,
            name = name,
            description = description,
            temperament = temperament,
            origin = origin,
            lifeSpan = lifeSpan,
            weight = weight?.toCached(),
            imageUrl = imageUrl,
        )
    }

    private fun CachedWeight.toDomain(): CatWeight {
        return CatWeight(
            imperial = imperial,
            metric = metric,
        )
    }

    private fun CatWeight.toCached(): CachedWeight {
        return CachedWeight(
            imperial = imperial,
            metric = metric,
        )
    }
}

