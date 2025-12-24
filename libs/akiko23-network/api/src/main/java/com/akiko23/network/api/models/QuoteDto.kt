package com.akiko23.network.api.models

import kotlinx.serialization.Serializable

/**
 * Модель цитаты из API.
 */
@Serializable
data class QuoteDto(
    val _id: String,
    val content: String,
    val author: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Int,
    val dateAdded: String,
    val dateModified: String
)

