package com.michaelnoskov.api.domain.model

data class SquareData(
    val id: String = "default",
    val color: Int,
    val size: Int,
    val rotation: Float,
    val alpha: Float = 1f
)

