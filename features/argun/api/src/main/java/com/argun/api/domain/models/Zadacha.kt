package com.argun.api.domain.models

data class Zadacha(
    val id: Int?,
    val title: String,
    val completed: Boolean,
    val userId: Int
)

