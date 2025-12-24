package com.momuswinner.network

import com.momuswinner.network.api.QuoteApiService
import com.momuswinner.network.api.models.QuoteDto

internal class RetrofitQuoteApiClient(
    private val service: QuoteService
) : QuoteApiService {
    override suspend fun getQuotes(): List<QuoteDto> {
        return service.getQuotes()
    }
}

