package com.example.ikr_application.egorik4.domain

import com.example.ikr_application.egorik4.data.BooksRepository
import com.example.ikr_application.egorik4.domain.models.Book

class GetBooksByGenreUseCase(
    private val repository: BooksRepository = BooksRepository()
) {
    fun execute(genre: String): List<Book> {
        return repository.getBooks()
            .filter { it.genre == genre }
            .map { dto ->
                Book(
                    title = dto.title,
                    author = dto.author,
                    year = dto.year,
                    rating = dto.rating,
                    genre = dto.genre
                )
            }
    }
}

