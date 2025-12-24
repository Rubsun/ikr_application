package com.akiko23.network.data

import com.akiko23.network.api.QuoteApiClient
import com.akiko23.network.api.models.QuoteDto
import kotlin.random.Random

/**
 * Реализация QuoteApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitQuoteApiClient(
    private val service: QuoteService,
    private val apiKey: String
) : QuoteApiClient {
    
    private val categories = listOf(
        "wisdom",
        "philosophy",
        "life",
        "truth",
        "inspirational",
        "relationships",
        "love",
        "faith",
        "humor",
        "success",
        "courage",
        "happiness",
        "art",
        "writing",
        "fear",
        "nature",
        "time",
        "freedom",
        "death",
        "leadership"
    )
    
    override suspend fun getRandomQuote(): QuoteDto {
        val randomCategory = categories.random(Random)
        val responses = service.getRandomQuote(apiKey, randomCategory)
        val response = if (responses.isNotEmpty()) {
            responses.random(Random)
        } else {
            throw IllegalStateException("API returned empty list")
        }
        
        return QuoteDto(
            _id = "",
            content = response.quote,
            author = response.author,
            tags = response.categories,
            authorSlug = "",
            length = response.quote.length,
            dateAdded = "",
            dateModified = ""
        )
    }
}

