package com.example.ikr_application.michaelnoskov.domain.model

data class SquareData(
    val id: String = "default",
    val color: Int,
    val size: Int,
    val rotation: Float,
    val alpha: Float = 1f
)