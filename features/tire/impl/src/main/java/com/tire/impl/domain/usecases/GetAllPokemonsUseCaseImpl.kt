package com.tire.impl.domain.usecases

import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.usecases.GetAllPokemonsUseCase
import com.tire.impl.data.repository.PokemonRepository

internal class GetAllPokemonsUseCaseImpl(
    private val repository: PokemonRepository
) : GetAllPokemonsUseCase {

    override suspend fun invoke(offset: Int, limit: Int): List<Pokemon> {
        return repository.getAllPokemonList(offset, limit)
    }
}
