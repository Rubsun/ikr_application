package com.tire.api.domain.usecases

import com.tire.api.domain.models.PokemonCase
import kotlinx.coroutines.flow.Flow


interface GetAllCasesUseCase {
    operator fun invoke(): Flow<List<PokemonCase>>
}
