package com.tire.network.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OtherSpritesDto(
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtworkDto? = null,

    val home: HomeSpritesDto? = null
)
