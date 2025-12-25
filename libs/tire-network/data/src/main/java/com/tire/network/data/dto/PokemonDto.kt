package com.tire.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonDto(
    val name: String,
    val url: String  // "https://pokeapi.co/api/v2/pokemon/25/"
) {
    val id: Int
        get() = url.trimEnd('/').split("/").last().toInt()
}
