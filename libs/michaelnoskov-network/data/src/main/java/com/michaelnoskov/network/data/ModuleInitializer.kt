package com.michaelnoskov.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.michaelnoskov.network.api.WeatherApiClient
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.open-meteo.com/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для michaelnoskov.
 * Регистрирует WeatherApiClient в Koin для использования в feature модуле.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Json { 
                        ignoreUnknownKeys = true
                        isLenient = true
                        coerceInputValues = true
                    }
                }

                single {
                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                    OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .build()
                }

                single<WeatherService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(WeatherService::class.java)
                }

                single<WeatherApiClient> {
                    RetrofitWeatherApiClient(get())
                }

                single<com.michaelnoskov.network.api.ColorSquareApiClient> {
                    ColorSquareApiClientImpl()
                }
            }
        )
    }
}

