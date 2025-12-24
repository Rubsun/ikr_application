package com.tire.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,  // дециметры
    val weight: Int,  // гектограммы (10 = 1 кг)
    val sprites: SpritesDto,
    val types: List<TypeSlotDto>
)
