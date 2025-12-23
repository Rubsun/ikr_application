package com.egorik4.network.data

import com.egorik4.network.data.models.OpenLibraryResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit интерфейс для работы с OpenLibrary API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface BooksService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20
    ): OpenLibraryResponse
}

