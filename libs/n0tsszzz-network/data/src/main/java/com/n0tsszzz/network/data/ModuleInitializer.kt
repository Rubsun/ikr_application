package com.n0tsszzz.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.n0tsszzz.network.api.TimeApiClient
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://worldtimeapi.org/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для n0tsszzz.
 * Регистрирует TimeApiClient в Koin для использования в feature модуле.
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
                        .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                        .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                        .writeTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .build()
                }

                single<TimeService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(TimeService::class.java)
                }

                single<TimeApiClient> {
                    RetrofitTimeApiClient(get())
                }
            }
        )
    }
}

