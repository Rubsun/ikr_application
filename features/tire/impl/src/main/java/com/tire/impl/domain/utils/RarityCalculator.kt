package com.tire.impl.domain.utils

import com.tire.api.domain.PokemonRarity

internal object RarityCalculator {

    // Легендарные покемоны
    private val legendaryIds = setOf(
        144,  // Articuno
        145,  // Zapdos
        146,  // Moltres
        150,  // Mewtwo
        151,   // Mew
    )

    // Эпические покемоны
    private val epicIds = setOf(
        3,
        6,
        9,
        65,
        68,
        94,
        130,
        131,
        149,
    )

    // Редкие покемоны
    private val rareIds = setOf(
        2, 5, 8,
        26, 28, 31,
        34, 36, 38,
        45, 59, 62,
        78, 80, 91,
        103, 106, 107,
        112, 115, 121,
        122, 127, 128,
        132, 135, 136, 137, 139, 141, 143,
    )

    fun calculateRarity(pokemonId: Int): PokemonRarity {
        return when (pokemonId) {
            in legendaryIds -> PokemonRarity.LEGENDARY
            in epicIds -> PokemonRarity.EPIC
            in rareIds -> PokemonRarity.RARE
            else -> PokemonRarity.COMMON
        }
    }
}
