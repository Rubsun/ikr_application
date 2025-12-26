package com.fomin.impl.domain

import android.util.Log
import com.fomin.api.domain.models.CatBreed
import com.fomin.api.domain.usecases.GetBreedDetailsUseCase
import com.fomin.impl.data.CatRepository
import com.fomin.impl.data.FominStorage
import java.io.IOException

private const val TAG = "FominUseCase"

internal class GetBreedDetailsUseCaseImpl(
    private val repository: CatRepository,
    private val storage: FominStorage,
) : GetBreedDetailsUseCase {

    override suspend fun invoke(breedId: String): Result<CatBreed> {
        Log.d(TAG, "🔍 Запрос деталей породы: breedId=$breedId")
        
        return try {
            // Сначала проверяем кеш
            val cachedBreed = storage.getBreed(breedId)
            if (cachedBreed != null) {
                Log.d(TAG, "✅ Данные найдены в кеше для breedId=$breedId, имя=${cachedBreed.name}")
                return Result.success(cachedBreed)
            }

            Log.d(TAG, "❌ Данные НЕ найдены в кеше для breedId=$breedId, загружаем из API...")
            
            // Если нет в кеше - загружаем из API
            val breed = repository.getBreedById(breedId)
            if (breed != null) {
                Log.d(TAG, "📥 Данные успешно загружены из API для breedId=$breedId, имя=${breed.name}")
                
                // Сохраняем в кеш для будущих запросов
                storage.saveBreed(breed)
                Log.d(TAG, "💾 Данные сохранены в кеш для breedId=$breedId")
                
                Result.success(breed)
            } else {
                Log.e(TAG, "❌ Порода не найдена в API: breedId=$breedId")
                Result.failure(IllegalArgumentException("Breed not found"))
            }
        } catch (e: IOException) {
            Log.w(TAG, "⚠️ Ошибка сети при загрузке breedId=$breedId: ${e.message}, пытаемся получить из кеша...")
            
            // При ошибке сети пытаемся вернуть из кеша
            val cachedBreed = storage.getBreed(breedId)
            if (cachedBreed != null) {
                Log.d(TAG, "✅ Fallback: данные найдены в кеше при ошибке сети для breedId=$breedId")
                Result.success(cachedBreed)
            } else {
                Log.e(TAG, "❌ Fallback: данные НЕ найдены в кеше при ошибке сети для breedId=$breedId")
                Result.failure(e)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ Неожиданная ошибка при загрузке breedId=$breedId: ${e.message}", e)
            Result.failure(e)
        }
    }
}

