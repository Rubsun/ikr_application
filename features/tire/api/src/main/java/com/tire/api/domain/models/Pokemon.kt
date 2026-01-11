package com.tire.api.domain.models

import com.tire.api.domain.PokemonRarity
import com.tire.api.domain.PokemonType

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val rarity: PokemonRarity,
    val ownedCount: Int,
)
