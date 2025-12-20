package com.denisova.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.denisova.api.Constants
import com.denisova.api.domain.usecases.AddWeatherLocationUseCase
import com.denisova.api.domain.usecases.GetWeatherLocationsUseCase
import com.denisova.api.domain.usecases.RefreshWeatherUseCase
import com.denisova.impl.data.GeocodingApiService
import com.denisova.impl.data.WeatherApiService
import com.denisova.impl.data.WeatherRepository
import com.denisova.impl.data.storage.DenisovaStorage
import com.denisova.impl.domain.AddWeatherLocationUseCaseImpl
import com.denisova.impl.domain.GetWeatherLocationsUseCaseImpl
import com.denisova.impl.domain.RefreshWeatherUseCaseImpl
import com.denisova.impl.ui.DenisovaFragment
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val FORECAST_BASE_URL = "https://api.open-meteo.com/"
private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"
private const val PREFS_NAME = "denisova_prefs"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { Json { ignoreUnknownKeys = true } }

                single {
                    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                    OkHttpClient.Builder().addInterceptor(logging).build()
                }

                single {
                    Retrofit.Builder()
                        .baseUrl(FORECAST_BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(WeatherApiService::class.java)
                }

                single {
                    Retrofit.Builder()
                        .baseUrl(GEOCODING_BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(GeocodingApiService::class.java)
                }

                single {
                    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                }

                single { DenisovaStorage(get(), get()) }

                single { WeatherRepository(get(), get(), get()) }

                factory<GetWeatherLocationsUseCase> { GetWeatherLocationsUseCaseImpl(get()) }
                factory<AddWeatherLocationUseCase> { AddWeatherLocationUseCaseImpl(get()) }
                factory<RefreshWeatherUseCase> { RefreshWeatherUseCaseImpl(get()) }

                factory<Class<out Fragment>>(named(Constants.DENISOVA_SCREEN)) {
                    DenisovaFragment::class.java
                }
            }
        )
    }
}
