package com.egorik4.network.data.models

import com.egorik4.network.api.models.BookDto
import kotlinx.serialization.Serializable

/**
 * Ответ от OpenLibrary API.
 * Внутренняя модель для работы с Retrofit, не должна использоваться вне data модуля.
 */
@Serializable
internal data class OpenLibraryResponse(
    val docs: List<BookDto>
)

