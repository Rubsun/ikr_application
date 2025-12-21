package com.egorik4.impl.data

import com.egorik4.impl.data.models.OpenLibraryResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BooksService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20
    ): OpenLibraryResponse
}
