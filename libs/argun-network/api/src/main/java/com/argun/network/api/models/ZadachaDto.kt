package com.argun.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ZadachaDto(
    val id: Int? = null,
    val title: String,
    val completed: Boolean = false,
    val userId: Int
)

