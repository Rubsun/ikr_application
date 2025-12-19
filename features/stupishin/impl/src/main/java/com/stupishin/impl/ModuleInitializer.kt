package com.stupishin.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.stupishin.api.Constants
import com.stupishin.api.domain.usecases.GetTopAnimeUseCase
import com.stupishin.api.domain.usecases.SearchAnimeUseCase
import com.stupishin.impl.data.AnimeRepository
import com.stupishin.impl.data.AnimeService
import com.stupishin.impl.data.StupishinStorage
import com.stupishin.impl.domain.GetTopAnimeUseCaseImpl
import com.stupishin.impl.domain.SearchAnimeUseCaseImpl
import com.stupishin.impl.ui.StuAnimeFragment
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://api.jikan.moe/"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Json { ignoreUnknownKeys = true }
                }

                single {
                    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                }

                single {
                    StupishinStorage(get(), get())
                }

                factory {
                    createService(BASE_URL, get())
                }

                single {
                    AnimeRepository(get())
                }

                factory<GetTopAnimeUseCase> {
                    GetTopAnimeUseCaseImpl(get(), get())
                }

                factory<SearchAnimeUseCase> {
                    SearchAnimeUseCaseImpl(get(), get())
                }

                factory<Class<out Fragment>>(named(Constants.STUPISHIN_SCREEN)) {
                    StuAnimeFragment::class.java
                }
            }
        )
    }

    private fun createService(baseUrl: String, json: Json): AnimeService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AnimeService::class.java)
    }

    private companion object {
        const val PREFS_NAME = "stupishin_prefs"
    }
}
