package com.egorik4.api.domain.models

data class Book(
    val title: String,
    val author: String,
    val year: Int?,
    val coverImageUrl: String?
)
