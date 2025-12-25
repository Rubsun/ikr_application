package com.rin2396.network.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rin2396.network.api.CataasCatApiClient
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

fun loadRin2396NetworkModule() {
    loadKoinModules(
        module {
            single {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            }

            single {
                val json = Json { ignoreUnknownKeys = true }
                Retrofit.Builder()
                    .baseUrl("https://cataas.com/")
                    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                    .client(get())
                    .build()
            }

            single {
                val retrofit = get<Retrofit>()
                val service = retrofit.create(CataasService::class.java)
                RetrofitCataasCatApiClient(service) as CataasCatApiClient
            }
        }
    )
}
