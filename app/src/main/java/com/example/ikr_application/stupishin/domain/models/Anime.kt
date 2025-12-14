package com.example.ikr_application.stupishin.domain.models

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val score: Double?,
    val episodes: Int?,
    val year: Int?,
)
