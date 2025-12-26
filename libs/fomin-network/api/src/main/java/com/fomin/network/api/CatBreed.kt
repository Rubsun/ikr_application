package com.fomin.network.api

data class CatBreed(
    val id: String,
    val name: String,
    val description: String?,
    val temperament: String?,
    val origin: String?,
    val lifeSpan: String?,
    val weight: CatWeight?,
    val image: CatImage?,
)

data class CatWeight(
    val imperial: String?,
    val metric: String?,
)


