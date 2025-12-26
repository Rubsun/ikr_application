package com.fomin.api.domain.usecases

import com.fomin.api.domain.models.CatImage

interface GetBreedImagesUseCase {
    suspend operator fun invoke(breedId: String, limit: Int = 10): Result<List<CatImage>>
}


