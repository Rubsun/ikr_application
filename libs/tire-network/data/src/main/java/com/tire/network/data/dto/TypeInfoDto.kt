package com.tire.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class TypeInfoDto(
    val name: String,
    val url: String
)