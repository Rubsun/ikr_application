package com.akiko23.network.data.models

import kotlinx.serialization.Serializable

/**
 * Модель ответа от API для цитаты (API Ninjas).
 */
@Serializable
internal data class QuoteResponse(
    val quote: String,
    val author: String,
    val work: String = "",
    val categories: List<String> = emptyList()
)

