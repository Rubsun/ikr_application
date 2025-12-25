package com.tire.impl.domain.usecases

import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.usecases.SearchPokemonsUseCase
import com.tire.impl.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

internal class SearchPokemonsUseCaseImpl(
    private val repository: PokemonRepository
) : SearchPokemonsUseCase {

    override fun invoke(query: String): Flow<List<Pokemon>> {
        return repository.searchPokemons(query)
    }
}
