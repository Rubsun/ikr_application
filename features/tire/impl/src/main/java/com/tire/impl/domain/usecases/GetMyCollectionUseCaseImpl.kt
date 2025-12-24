package com.tire.impl.domain.usecases

import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.usecases.GetMyCollectionUseCase
import com.tire.impl.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

internal class GetMyCollectionUseCaseImpl(
    private val repository: PokemonRepository
) : GetMyCollectionUseCase {

    override fun invoke(): Flow<List<Pokemon>> {
        return repository.getMyCollection()
    }
}
