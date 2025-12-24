package com.akiko23.network.data

import android.content.Context
import com.akiko23.network.api.Akiko23NetworkClient
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.quotable.io/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для akiko23.
 * Регистрирует Akiko23NetworkClient в Koin для использования в feature модуле.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Json { ignoreUnknownKeys = true }
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
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(QuoteService::class.java)
                }

                single<Akiko23NetworkClient> {
                    RetrofitQuoteApiClient(get())
                }
            }
        )
    }
}

