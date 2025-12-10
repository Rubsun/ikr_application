package com.example.ikr_application.egorik4.domain

import com.example.ikr_application.egorik4.data.BooksRepository
import com.example.ikr_application.egorik4.domain.models.Book

class GetTopRatedBooksUseCase(
    private val repository: BooksRepository = BooksRepository()
) {
    fun execute(topCount: Int = 5): List<Book> {
        return repository.getBooks()
            .map { dto ->
                Book(
                    title = dto.title,
                    author = dto.author,
                    year = dto.year,
                    rating = dto.rating,
                    genre = dto.genre
                )
            }
            .sortedByDescending { it.rating }
            .take(topCount)
    }
}

