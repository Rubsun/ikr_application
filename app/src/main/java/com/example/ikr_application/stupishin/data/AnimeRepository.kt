package com.example.ikr_application.stupishin.data

import com.example.ikr_application.stupishin.data.models.AnimeDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://api.jikan.moe/"

internal class AnimeRepository {
    private val api by lazy { createService() }

    suspend fun topAnime(page: Int = 1): List<AnimeDto> {
        return withContext(Dispatchers.IO) {
            api.topAnime(page = page).data
        }
    }

    suspend fun searchAnime(query: String, page: Int = 1, limit: Int = 25): List<AnimeDto> {
        return withContext(Dispatchers.IO) {
            api.searchAnime(query = query, page = page, limit = limit).data
        }
    }

    private fun createService(): AnimeService {
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
            .create(AnimeService::class.java)
    }

    companion object {
        val INSTANCE = AnimeRepository()
    }
}
