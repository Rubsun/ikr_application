package com.tire.api.domain.models

import com.tire.api.domain.PokemonType
import com.tire.api.domain.PokemonRarity

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val rarity: PokemonRarity,
    val isOwned: Boolean = false,
)
