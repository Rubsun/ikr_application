package com.egorik4.api.domain.usecases

import com.egorik4.api.domain.models.Book

interface SearchBooksUseCase {
    suspend operator fun invoke(query: String): Result<List<Book>>
}
