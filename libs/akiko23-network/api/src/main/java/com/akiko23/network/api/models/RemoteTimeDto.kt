package com.akiko23.network.api.models

import kotlinx.serialization.Serializable

/**
 * DTO для цитаты, полученной из внешнего API.
 */
@Serializable
data class QuoteDto(
    val content: String,
    val author: String
)

