package com.tire.network.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SpritesDto(
    @SerialName("front_default")
    val frontDefault: String?,

    val other: OtherSpritesDto? = null
)
