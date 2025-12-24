package com.momuswinner.api.domain

import com.momuswinner.api.domain.models.Quote

interface GetQuoteUseCase {
    suspend fun getQuote(): Quote
}
