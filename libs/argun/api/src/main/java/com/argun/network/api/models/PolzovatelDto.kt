package com.argun.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PolzovatelDto(
    val id: Int? = null,
    val name: String,
    val username: String,
    val email: String
)

