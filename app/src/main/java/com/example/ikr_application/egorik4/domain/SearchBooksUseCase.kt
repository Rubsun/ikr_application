package com.example.ikr_application.egorik4.domain

import com.example.ikr_application.egorik4.data.BooksRepository
import com.example.ikr_application.egorik4.data.models.BookDto
import com.example.ikr_application.egorik4.domain.models.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.success

internal class SearchBooksUseCase(
    private val repository: BooksRepository = BooksRepository.INSTANCE
) {
    suspend operator fun invoke(query: String): Result<List<Book>> = withContext(Dispatchers.IO) {
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

