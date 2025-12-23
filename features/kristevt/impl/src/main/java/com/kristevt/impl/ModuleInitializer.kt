package com.kristevt.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kristevt.api.Constants
import com.kristevt.api.domain.GetLyricsUseCase
import com.kristevt.api.domain.SongsListUseCase
import com.kristevt.impl.data.LyricsRepository
import com.kristevt.impl.data.LyricsService
import com.kristevt.impl.data.SongsRepository
import com.kristevt.impl.domain.GetLyricsUseCaseImpl
import com.kristevt.impl.domain.SongsListUseCaseImpl
import com.kristevt.impl.ui.KristevtFragment
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.lyrics.ovh/"

/**
 * Модуль для конфигурации зависимостей
 * Его надо добавлять в манифест, что бы KOIN + startup их собрали
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                factory<LyricsService> { createService(BASE_URL) }

                single { SongsRepository(context = get())}
                single { LyricsRepository() }

                factory<SongsListUseCase> { SongsListUseCaseImpl(get()) }
                factory<GetLyricsUseCase> { GetLyricsUseCaseImpl(get()) }

                factory<Class<out Fragment>>(named(Constants.KRISTEVT_SCREEN)) { KristevtFragment::class.java }
            }
        )
    }

    private fun createService(baseUrl: String): LyricsService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(LyricsService::class.java)
    }
}
