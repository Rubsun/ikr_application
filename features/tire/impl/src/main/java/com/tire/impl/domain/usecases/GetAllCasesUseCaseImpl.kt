package com.tire.impl.domain.usecases

import com.tire.api.domain.models.PokemonCase
import com.tire.api.domain.usecases.GetAllCasesUseCase
import com.tire.impl.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GetAllCasesUseCaseImpl(
    private val repository: PokemonRepository
) : GetAllCasesUseCase {

    override fun invoke(): Flow<List<PokemonCase>> = flow {
        val cases = repository.getAllCases()
        emit(cases)
    }
}
