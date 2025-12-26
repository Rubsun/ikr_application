package com.fomin.impl.domain

import com.fomin.api.domain.models.CatBreed
import com.fomin.api.domain.usecases.GetBreedsUseCase
import com.fomin.impl.data.CatRepository
import java.io.IOException

internal class GetBreedsUseCaseImpl(
    private val repository: CatRepository,
) : GetBreedsUseCase {

    override suspend fun invoke(): Result<List<CatBreed>> {
        return try {
            val breeds = repository.getBreeds()
            Result.success(breeds)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


