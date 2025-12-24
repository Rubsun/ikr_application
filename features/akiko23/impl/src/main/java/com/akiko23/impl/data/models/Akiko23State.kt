package com.akiko23.impl.data.models

import kotlinx.serialization.Serializable

/**
 * Модель состояния для сохранения в персистентное хранилище.
 */
@Serializable
internal data class Akiko23State(
    val lastQuote: String? = null,
    val lastImageUrl: String? = null,
)

