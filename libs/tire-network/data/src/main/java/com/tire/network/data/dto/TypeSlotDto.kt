package com.tire.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class TypeSlotDto(
    val slot: Int,
    val type: TypeInfoDto
)
