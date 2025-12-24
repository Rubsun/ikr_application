package com.tire.impl.domain.usecases

import com.tire.api.domain.models.CasePreview
import com.tire.api.domain.usecases.GetCasePreviewUseCase
import com.tire.impl.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class GetCasePreviewUseCaseImpl(
    private val repository: PokemonRepository
) : GetCasePreviewUseCase {
    override suspend fun invoke(caseId: String): Flow<CasePreview> = flow {
        val pokemonCase = repository.getCaseById(caseId)
            ?: throw IllegalArgumentException("Case not found: $caseId")
        val allPokemonIds = pokemonCase.lootTable.flatMap { it.pokemonIds }
        val pokemons = repository.getPokemonsByIds(allPokemonIds)
        val sortedPokemons = pokemons.sortedBy { it.rarity.ordinal }
        val caseName = pokemonCase.name
        val caseInfo = "${sortedPokemons.size} pokemons"
        val caseImageUrl = pokemonCase.imageUrl
        emit(
            CasePreview(
                caseName = caseName,
                caseInfo = caseInfo,
                pokemons = sortedPokemons,
                caseImageUrl = caseImageUrl,
            )
        )
    }.flowOn(Dispatchers.IO)
}

