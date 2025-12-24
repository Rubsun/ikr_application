package com.tire.impl.data.config.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CasesConfigDto(
    val cases: List<PokemonCaseDto>,
)
