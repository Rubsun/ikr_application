package com.rin2396.api.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CatModel(
    val id: String,
    val imageUrl: String,
    val tag: String = "random",
    val addedAt: Long = System.currentTimeMillis()
)
