package com.tire.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PokemonShort(
    val id: Int,
    val name: String,
    val imageUrl: String?
)