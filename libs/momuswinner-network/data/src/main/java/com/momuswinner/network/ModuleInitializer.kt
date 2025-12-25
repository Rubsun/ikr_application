package com.momuswinner.network

import android.content.Context
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.momuswinner.network.api.QuoteApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.breakingbadquotes.xyz/"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                }

                single {
                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build()
                }

                single<QuoteService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(ListConverterFactory(get()))
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(QuoteService::class.java)
                }

                single<QuoteApiService> {
                    RetrofitQuoteApiClient(get())
                }


//
//                val loggingInterceptor = HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
//                }
//
//                val okHttpClient = OkHttpClient.Builder()
//                    .addInterceptor(loggingInterceptor)
//                    .build()
//
//                val json = Json { ignoreUnknownKeys = true }
//
//                single<QuoteService> {
//                    Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(okHttpClient)
//                    .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
//                    .build()
//                    .create(QuoteService::class.java)
//                }
            }
        )
    }
}
