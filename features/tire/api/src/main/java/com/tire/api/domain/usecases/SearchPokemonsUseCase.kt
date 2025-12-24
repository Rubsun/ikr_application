package com.tire.api.domain.usecases

import com.tire.api.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface SearchPokemonsUseCase {
    operator fun invoke(query: String): Flow<List<Pokemon>>
}
