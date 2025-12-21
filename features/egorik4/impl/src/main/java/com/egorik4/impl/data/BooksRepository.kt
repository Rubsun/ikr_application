package com.egorik4.impl.data

import com.egorik4.impl.data.models.BookDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://openlibrary.org/"

internal class BooksRepository(
    private val service: BooksService
) {
    suspend fun searchBooks(query: String): List<BookDto> {
        return service.searchBooks(query).docs
    }
}

internal fun createBooksService(baseUrl: String = BASE_URL): BooksService {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val json = Json { ignoreUnknownKeys = true }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(BooksService::class.java)
}
