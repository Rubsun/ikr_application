package com.example.ikr_application.egorik4.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.egorik4.domain.GetBooksByGenreUseCase
import com.example.ikr_application.egorik4.domain.GetBooksUseCase
import com.example.ikr_application.egorik4.domain.GetTopRatedBooksUseCase
import com.example.ikr_application.egorik4.ui.models.BookDisplayModel

class Egorik4ViewModel : ViewModel() {
    private val getBooksUseCase = GetBooksUseCase()
    private val getTopRatedBooksUseCase = GetTopRatedBooksUseCase()
    private val getBooksByGenreUseCase = GetBooksByGenreUseCase()

    fun getAllBooks(): List<BookDisplayModel> {
        return getBooksUseCase.execute().map { book ->
            BookDisplayModel(
                displayTitle = book.title,
                displayAuthor = book.author,
                displayInfo = "${book.year}, ${book.genre}, ⭐ ${book.rating}"
            )
        }
    }

    fun getTopRatedBooks(): List<BookDisplayModel> {
        return getTopRatedBooksUseCase.execute().map { book ->
            BookDisplayModel(
                displayTitle = book.title,
                displayAuthor = book.author,
                displayInfo = "${book.year}, ${book.genre}, ⭐ ${book.rating}"
            )
        }
    }

    fun getBooksByGenre(genre: String): List<BookDisplayModel> {
        return getBooksByGenreUseCase.execute(genre).map { book ->
            BookDisplayModel(
                displayTitle = book.title,
                displayAuthor = book.author,
                displayInfo = "${book.year}, ${book.genre}, ⭐ ${book.rating}"
            )
        }
    }
}

