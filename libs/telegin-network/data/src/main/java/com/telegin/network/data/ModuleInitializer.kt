package com.telegin.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.telegin.network.api.WeatherApiClient
import com.telegin.network.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для telegin.
 * Регистрирует WeatherApiClient в Koin для использования в feature модуле.
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

                single<WeatherService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(WeatherService::class.java)
                }

                single<WeatherApiClient> {
                    RetrofitWeatherApiClient(get(), BuildConfig.OPEN_WEATHER_API_KEY)
                }
            }
        )
    }
}

