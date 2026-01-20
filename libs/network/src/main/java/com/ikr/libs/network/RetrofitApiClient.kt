package com.ikr.libs.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit

/**
 * Реализация ApiClient через Retrofit
 */
class RetrofitApiClient(
    baseUrl: String,
    apiKey: String? = null
) : ApiClient {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            
            apiKey?.let {
                builder.header("X-Api-Key", it)
            }
            
            builder.header("Content-Type", "application/json")
            chain.proceed(builder.build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    override suspend fun <T> execute(request: ApiRequest<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                // Упрощенная реализация - в реальном проекте нужна более сложная логика
                ApiResponse.Error("Not implemented in this example")
            } catch (e: Exception) {
                ApiResponse.Error(e.message ?: "Unknown error")
            }
        }
    }
}

