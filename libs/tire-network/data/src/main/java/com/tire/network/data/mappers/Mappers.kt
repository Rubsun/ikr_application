package com.tire.network.data.mappers

import com.tire.network.api.models.PokemonDetail
import com.tire.network.api.models.PokemonListResponse
import com.tire.network.api.models.PokemonShort
import com.tire.network.data.dto.PokemonDetailDto
import com.tire.network.data.dto.PokemonDto
import com.tire.network.data.dto.PokemonListResponseDto

internal fun PokemonListResponseDto.toApiModel(): PokemonListResponse =
    PokemonListResponse(
        count = count,
        next = next,
        previous = previous,
        results = results.map { it.toApiModel() }
    )

internal fun PokemonDto.toApiModel(): PokemonShort =
    PokemonShort(
        id = id,
        name = name,
        imageUrl = url
    )

internal fun PokemonDetailDto.toApiModel(): PokemonDetail =
    PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        imageUrl = sprites.other?.officialArtwork?.frontDefault
            ?: sprites.frontDefault,
        types = types.map { it.type.name }
    )
