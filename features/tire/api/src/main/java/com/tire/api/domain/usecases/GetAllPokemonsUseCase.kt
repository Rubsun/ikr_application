package com.tire.api.domain.usecases

import com.tire.api.domain.models.Pokemon

interface GetAllPokemonsUseCase {
    suspend operator fun invoke(offset: Int = 0, limit: Int = 151): List<Pokemon>
}
