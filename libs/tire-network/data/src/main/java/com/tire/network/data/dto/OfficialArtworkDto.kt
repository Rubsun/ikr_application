package com.tire.network.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OfficialArtworkDto(
    @SerialName("front_default")
    val frontDefault: String?
)
