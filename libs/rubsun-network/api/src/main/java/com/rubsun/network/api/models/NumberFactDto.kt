package com.rubsun.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    val displayTitle: String
        get() = title.ifEmpty { "Без названия" }
    
    val displayBody: String
        get() = body.ifEmpty { "Нет описания" }
}
