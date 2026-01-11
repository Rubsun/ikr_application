package com.kristevt.impl.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kristevt.impl.data.models.LyricsDto
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://api.lyrics.ovh/"

internal class LyricsRepository {

    private val api by lazy { createService() }

    suspend fun getLyrics(artist: String, title: String): LyricsDto {
        return api.getLyrics(artist, title)
    }

    private fun createService(): LyricsService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(LyricsService::class.java)
    }

    companion object {
        val INSTANCE = LyricsRepository()
    }
}
