package com.spl3g.network.data

import android.content.Context
import com.egorik4.network.data.RetrofitAppleApiClient
import com.example.injector.AbstractInitializer
import com.spl3g.network.api.AppleApiClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://kcu.su/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для egorik4.
 * Регистрирует BooksApiClient в Koin для использования в feature модуле.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {

                single<AppleService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(AppleService::class.java)
                }

                single<AppleApiClient> {
                    RetrofitAppleApiClient(get())
                }
            }
        )
    }
}

