package com.egorik4.network.data

import android.content.Context
import com.egorik4.network.api.BooksApiClient
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://openlibrary.org/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для egorik4.
 * Регистрирует BooksApiClient в Koin для использования в feature модуле.
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

                single<BooksService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(BooksService::class.java)
                }

                single<BooksApiClient> {
                    RetrofitBooksApiClient(get())
                }
            }
        )
    }
}

