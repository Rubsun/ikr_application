package com.fomin.api.domain.usecases

import com.fomin.api.domain.models.CatBreed

interface GetBreedsUseCase {
    suspend operator fun invoke(): Result<List<CatBreed>>
}


