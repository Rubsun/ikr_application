package com.tire.impl.domain.usecases

import com.tire.api.domain.PokemonRarity
import com.tire.api.domain.models.CollectionStats
import com.tire.api.domain.usecases.GetCollectionStatsUseCase
import com.tire.impl.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetCollectionStatsUseCaseImpl(
    private val repository: PokemonRepository
) : GetCollectionStatsUseCase {

    private val TOTAL_POSSIBLE = 151

    override fun invoke(): Flow<CollectionStats> {
        return repository.getMyCollection().map { collection ->
            val commonOwned = collection.count { it.rarity == PokemonRarity.COMMON }
            val rareOwned = collection.count { it.rarity == PokemonRarity.RARE }
            val epicOwned = collection.count { it.rarity == PokemonRarity.EPIC }
            val legendaryOwned = collection.count { it.rarity == PokemonRarity.LEGENDARY }

            val totalOwned = collection.size
            val completionPercentage = if (TOTAL_POSSIBLE > 0) {
                (totalOwned.toFloat() / TOTAL_POSSIBLE.toFloat()) * 100f
            } else {
                0f
            }

            CollectionStats(
                totalOwned = totalOwned,
                totalPossible = TOTAL_POSSIBLE,
                commonOwned = commonOwned,
                rareOwned = rareOwned,
                epicOwned = epicOwned,
                legendaryOwned = legendaryOwned,
                completionPercentage = completionPercentage
            )
        }
    }
}
