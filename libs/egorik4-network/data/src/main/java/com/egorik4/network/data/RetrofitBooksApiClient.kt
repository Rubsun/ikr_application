package com.egorik4.network.data

import com.egorik4.network.api.BooksApiClient
import com.egorik4.network.api.models.BookDto

/**
 * Реализация BooksApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitBooksApiClient(
    private val service: BooksService
) : BooksApiClient {
    override suspend fun searchBooks(query: String): List<BookDto> {
        return service.searchBooks(query).docs
    }
}

