package com.tire.impl.data.config.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class RarityDto {
    @SerialName("COMMON")
    COMMON,

    @SerialName("RARE")
    RARE,

    @SerialName("EPIC")
    EPIC,

    @SerialName("LEGENDARY")
    LEGENDARY,
}
