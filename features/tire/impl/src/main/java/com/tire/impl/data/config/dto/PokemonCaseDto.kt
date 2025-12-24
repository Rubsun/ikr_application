package com.tire.impl.data.config.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonCaseDto(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val lootTable: List<LootEntryDto>,
)
