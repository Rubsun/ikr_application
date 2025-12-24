package com.tire.api.domain.models

import com.tire.api.domain.PokemonRarity

data class LootEntry(
    val pokemonIds: List<Int>,
    val rarity: PokemonRarity,
    val dropChance: Float
)