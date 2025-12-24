package com.tire.impl.domain.usecases

import com.tire.api.domain.models.CaseOpenResult
import com.tire.api.domain.usecases.OpenCaseUseCase
import com.tire.impl.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

internal class OpenCaseUseCaseImpl(
    private val repository: PokemonRepository
) : OpenCaseUseCase {

    override suspend fun invoke(caseId: String): Flow<CaseOpenResult> = flow {
        val pokemonCase = repository.getCaseById(caseId)
            ?: throw IllegalArgumentException("Case not found: $caseId")
        val selectedEntry = selectLootEntryByChance(pokemonCase.lootTable)
        val randomPokemonId = selectedEntry.pokemonIds.random()
        val pokemon = repository.getPokemonById(randomPokemonId)
        val isNew = !repository.isPokemonOwned(pokemon.id)
        val duplicateCount = repository.getPokemonDuplicateCount(pokemon.id)
        repository.savePokemonToCollection(pokemon)
        emit(CaseOpenResult(
            pokemon = pokemon,
            isNew = isNew,
            duplicateCount = duplicateCount
        ))
    }.flowOn(Dispatchers.IO)

    private fun selectLootEntryByChance(lootTable: List<com.tire.api.domain.models.LootEntry>): com.tire.api.domain.models.LootEntry {
        val random = Random.nextFloat()
        var accumulated = 0f
        for (entry in lootTable) {
            accumulated += entry.dropChance
            if (random <= accumulated) {
                return entry
            }
        }
        return lootTable.last()
    }
}
