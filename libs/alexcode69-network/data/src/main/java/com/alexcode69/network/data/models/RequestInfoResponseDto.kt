package com.alexcode69.network.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class RequestInfoResponseDto(
    val url: String,
    val origin: String
)

