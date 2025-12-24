package com.akiko23.network.data

import com.akiko23.network.data.models.QuoteResponse
import retrofit2.http.GET

/**
 * Retrofit интерфейс для работы с Quotes API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface QuoteService {
    @GET("random")
    suspend fun getRandomQuote(): QuoteResponse
}

