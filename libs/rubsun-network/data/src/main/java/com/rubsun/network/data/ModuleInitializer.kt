package com.rubsun.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rubsun.network.api.NumberApiClient
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

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
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(loggingInterceptor)
                        .build()
                }

                single<NumberService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(NumberService::class.java)
                }

                single<NumberApiClient> {
                    RetrofitNumberApiClient(get())
                }
            }
        )
    }
}
