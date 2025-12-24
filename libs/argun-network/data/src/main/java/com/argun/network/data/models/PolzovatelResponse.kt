package com.argun.network.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class PolzovatelResponse(
    val id: Int? = null,
    val name: String,
    val username: String,
    val email: String
)

