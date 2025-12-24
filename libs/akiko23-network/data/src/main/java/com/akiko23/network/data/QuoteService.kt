package com.akiko23.network.data

import com.akiko23.network.data.models.QuoteResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Retrofit интерфейс для работы с API Ninjas Quotes API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface QuoteService {
    @GET("quotes")
    suspend fun getRandomQuote(
        @Header("X-Api-Key") apiKey: String,
        @Query("categories") category: String
    ): List<QuoteResponse>
}

