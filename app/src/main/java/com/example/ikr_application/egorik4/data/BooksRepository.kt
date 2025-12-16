package com.example.ikr_application.egorik4.data

import androidx.annotation.Discouraged
import com.example.ikr_application.egorik4.data.models.BookDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://openlibrary.org/"

internal class BooksRepository {
    private val api by lazy { createService() }

    suspend fun searchBooks(query: String): List<BookDto> {
        return api.searchBooks(query).docs
    }

    private fun createService(): BooksService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(BooksService::class.java)
    }

    companion object {
        @Discouraged("Only for example")
        val INSTANCE = BooksRepository()
    }
}

