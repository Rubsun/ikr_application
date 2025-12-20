package com.dyatlova.api.domain.models

data class Destination(
    val id: String,
    val title: String,
    val country: String,
    val imageUrl: String,
    val tags: List<String>,
)

