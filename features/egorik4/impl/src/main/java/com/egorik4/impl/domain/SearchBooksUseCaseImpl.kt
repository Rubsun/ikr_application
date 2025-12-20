package com.egorik4.impl.domain

import com.egorik4.api.domain.models.Book
import com.egorik4.api.domain.usecases.SearchBooksUseCase
import com.egorik4.impl.data.BooksRepository
import com.egorik4.impl.data.models.BookDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.success

internal class SearchBooksUseCaseImpl(
    private val repository: BooksRepository
) : SearchBooksUseCase {
    override suspend fun invoke(query: String): Result<List<Book>> = withContext(Dispatchers.IO) {
        if (query.isBlank()) {
            return@withContext success(emptyList())
        }

        runCatching {
            repository.searchBooks(query)
                .filter { it.title != null && it.author_name != null && !it.author_name.isEmpty() }
                .map(::mapBook)
        }
    }

    private fun mapBook(dto: BookDto): Book {
        val author = dto.author_name?.firstOrNull() ?: "Unknown"
        val title = dto.title ?: "Untitled"
        val year = dto.first_publish_year
        val coverImageUrl = dto.cover_i?.let { coverId ->
            "https://covers.openlibrary.org/b/id/$coverId-M.jpg"
        }

        return Book(
            title = title,
            author = author,
            year = year,
            coverImageUrl = coverImageUrl
        )
    }
}
