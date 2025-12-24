package com.dimmension.network.data

import com.dimmension.network.api.DimmensionNetworkClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

internal class RetrofitNetworkClient : DimmensionNetworkClient {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    private val retrofitCache = mutableMapOf<String, Retrofit>()

    override fun <T : Any> createService(baseUrl: String, serviceClass: KClass<T>): T {
        val retrofit = retrofitCache.getOrPut(baseUrl) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }
        return retrofit.create(serviceClass.java)
    }
}

