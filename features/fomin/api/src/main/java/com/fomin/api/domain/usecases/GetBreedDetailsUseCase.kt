package com.fomin.api.domain.usecases

import com.fomin.api.domain.models.CatBreed

interface GetBreedDetailsUseCase {
    suspend operator fun invoke(breedId: String): Result<CatBreed>
}


