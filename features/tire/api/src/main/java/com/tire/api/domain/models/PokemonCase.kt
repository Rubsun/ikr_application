package com.tire.api.domain.models

data class PokemonCase(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val lootTable: List<LootEntry>
)
