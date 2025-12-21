package com.momuswinner.impl.data

import kotlinx.serialization.Serializable

@Serializable
internal data class PointDto(
    val x: Double,
    val y: Double
)
