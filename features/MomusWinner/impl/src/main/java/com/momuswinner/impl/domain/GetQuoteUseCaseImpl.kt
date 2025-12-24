package com.momuswinner.impl.domain

import com.momuswinner.api.domain.GetQuoteUseCase
import com.momuswinner.api.domain.models.Quote
import com.momuswinner.network.api.QuoteApiService

internal class GetQuoteUseCaseImpl(
    private val api: QuoteApiService
) : GetQuoteUseCase {
    override suspend fun getQuote(): Quote {
        val quote = api.getQuotes()[0]
        return Quote(quote = quote.quote, author = quote.author)
    }
}
