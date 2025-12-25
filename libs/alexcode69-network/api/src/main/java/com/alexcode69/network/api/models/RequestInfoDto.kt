package com.alexcode69.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestInfoDto(
    val url: String,
    val origin: String
)

