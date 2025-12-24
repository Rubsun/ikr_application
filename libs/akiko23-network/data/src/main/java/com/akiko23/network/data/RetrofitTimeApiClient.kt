package com.akiko23.network.data

import com.akiko23.network.api.Akiko23NetworkClient
import com.akiko23.network.api.models.QuoteDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Реализация Akiko23NetworkClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitQuoteApiClient(
    private val service: QuoteService
) : Akiko23NetworkClient {
    override suspend fun getRandomQuote(): QuoteDto = withContext(Dispatchers.IO) {
        val response = service.getRandomQuote()
        QuoteDto(
            content = response.content,
            author = response.author
        )
    }
}

