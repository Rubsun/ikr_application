package com.momuswinner.network.api

import com.momuswinner.network.api.models.QuoteDto

interface QuoteApiService {
    suspend fun getQuotes(): List<QuoteDto>
}

