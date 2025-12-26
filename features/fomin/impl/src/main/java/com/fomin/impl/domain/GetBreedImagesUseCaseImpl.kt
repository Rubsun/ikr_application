package com.fomin.impl.domain

import com.fomin.api.domain.models.CatImage
import com.fomin.api.domain.usecases.GetBreedImagesUseCase
import com.fomin.impl.data.CatRepository
import java.io.IOException

internal class GetBreedImagesUseCaseImpl(
    private val repository: CatRepository,
) : GetBreedImagesUseCase {

    override suspend fun invoke(breedId: String, limit: Int): Result<List<CatImage>> {
        return try {
            val images = repository.getBreedImages(breedId, limit)
            Result.success(images)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


