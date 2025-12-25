package com.tire.storage.api.models

data class PokemonRecord(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String,
    val rarity: String,
    val ownedCount: Int,
    val firstObtainedAt: Long?
)
