package com.ikr.app

import android.app.Application
import com.ikr.features.tasks.impl.TasksFeatureInitializer
import com.ikr.libs.imageloader.CoilImageLoader
import com.ikr.libs.imageloader.ImageLoader
import com.ikr.libs.network.ApiClient
import com.ikr.libs.network.RetrofitApiClient
import com.ikr.libs.storage.SharedPreferencesStorageProvider
import com.ikr.libs.storage.StorageProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                TasksFeatureInitializer.getModule()
            )
        }
    }
}

private val appModule = org.koin.dsl.module {
    // Storage Provider
    single<StorageProvider> {
        SharedPreferencesStorageProvider(
            context = get(),
            preferencesName = "app_preferences"
        )
    }

    // API Client
    // Для демонстрации используем фиктивный URL
    // В реальном проекте URL и API ключ должны быть в конфигурации
    single<ApiClient> {
        RetrofitApiClient(
            baseUrl = "https://api.example.com/",
            apiKey = null // API ключ должен быть получен из конфигурации
        )
    }

    // Image Loader
    single<ImageLoader> {
        CoilImageLoader()
    }
}

