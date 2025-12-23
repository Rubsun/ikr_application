package com.egorik4.impl.data

import com.egorik4.network.api.BooksApiClient
import com.egorik4.network.api.models.BookDto

internal class BooksRepository(
    private val apiClient: BooksApiClient
) {
    suspend fun searchBooks(query: String): List<BookDto> {
        return apiClient.searchBooks(query)
    }
}
