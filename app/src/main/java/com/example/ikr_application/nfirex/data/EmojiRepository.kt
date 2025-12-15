package com.example.ikr_application.nfirex.data

import androidx.annotation.Discouraged
import com.example.ikr_application.nfirex.data.models.EmojiDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private const val BASE_URL = "https://emojihub.yurace.pro/"

internal class EmojiRepository {
    private val api by lazy { createService() }

    suspend fun searchEmojis(query: String): List<EmojiDto> {
        return api.searchEmojis(query)
    }

    private fun createService(): EmojiService {
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
            .create(EmojiService::class.java)
    }

    companion object {
        @Discouraged("Only for example")
        val INSTANCE = EmojiRepository()
    }
}