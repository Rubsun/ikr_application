package com.fomin.api.domain.models

data class CatBreed(
    val id: String,
    val name: String,
    val description: String?,
    val temperament: String?,
    val origin: String?,
    val lifeSpan: String?,
    val weight: CatWeight?,
    val imageUrl: String?,
)

data class CatWeight(
    val imperial: String?,
    val metric: String?,
)


