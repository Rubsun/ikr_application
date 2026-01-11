package com.alexcode69.network.data

import android.content.Context
import com.alexcode69.network.api.Alexcode69ApiClient
import com.example.injector.AbstractInitializer
import com.example.network.api.RetrofitServiceFactory
import kotlinx.serialization.json.Json
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private const val BASE_URL = "https://httpbin.org/"

/**
 * Модуль для конфигурации зависимостей сетевого клиента для alexcode69.
 * Регистрирует Alexcode69ApiClient в Koin для использования в feature модуле.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { Json { ignoreUnknownKeys = true } }

                single<RequestInfoService> {
                    get<RetrofitServiceFactory>().create(
                        BASE_URL,
                        RequestInfoService::class.java
                    )
                }

                single<Alexcode69ApiClient> {
                    RetrofitAlexcode69ApiClient(get())
                }
            }
        )
    }
}

