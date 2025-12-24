package com.vtyapkova.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.google.gson.Gson
import com.vtyapkova.network.api.RandomUserApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для vtyapkova.
 * Регистрирует RandomUserApiClient в Koin для использования в feature модуле.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
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
                    Gson()
                }

                single<RandomUserApiClient> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(GsonConverterFactory.create(get()))
                        .build()
                        .create(RandomUserApiClient::class.java)
                }
            }
        )
    }
}

