package com.tire.impl.data.config.dto

import kotlinx.serialization.Serializable


@Serializable
internal data class LootEntryDto(
    val pokemonIds: List<Int>,
    val rarity: RarityDto,
    val dropChance: Float,
)
