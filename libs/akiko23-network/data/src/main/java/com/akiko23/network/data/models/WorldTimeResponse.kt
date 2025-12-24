package com.akiko23.network.data.models

import kotlinx.serialization.Serializable

/**
 * Ответ от Quotes API.
 * Внутренняя модель для работы с Retrofit, не должна использоваться вне data модуля.
 */
@Serializable
internal data class QuoteResponse(
    val content: String,
    val author: String
)

