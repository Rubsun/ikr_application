package com.example.api

data class CatDto(
    val name: String,
    val phrase: String?,
    val imageUrl: String?,
    val fetchedFrom: String
)