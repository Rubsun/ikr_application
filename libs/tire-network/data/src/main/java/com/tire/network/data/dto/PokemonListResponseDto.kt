package com.tire.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>,
)
