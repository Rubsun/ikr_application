package com.egorik4.network.api

import com.egorik4.network.api.models.BookDto

/**
 * Клиент для работы с API книг.
 * Абстракция для получения данных о книгах из внешнего источника.
 */
interface BooksApiClient {
    /**
     * Поиск книг по запросу.
     *
     * @param query поисковый запрос
     * @return список найденных книг
     */
    suspend fun searchBooks(query: String): List<BookDto>
}

