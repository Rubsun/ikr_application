package com.argun.network.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class ZadachaResponse(
    val id: Int? = null,
    val title: String,
    val completed: Boolean = false,
    val userId: Int
)

