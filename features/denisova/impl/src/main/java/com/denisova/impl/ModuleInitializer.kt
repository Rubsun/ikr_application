package com.denisova.impl

import android.content.Context
import com.denisova.api.Constants
import com.denisova.api.domain.usecases.AddWeatherLocationUseCase
import com.denisova.api.domain.usecases.GetWeatherLocationsUseCase
import com.denisova.api.domain.usecases.RefreshWeatherUseCase
import com.denisova.impl.data.GeocodingApiService
import com.denisova.impl.data.HttpGeocodingApiService
import com.denisova.impl.data.HttpWeatherApiService
import com.denisova.impl.data.WeatherApiService
import com.denisova.impl.data.WeatherRepository
import com.denisova.impl.data.storage.DenisovaStorage
import com.denisova.impl.domain.AddWeatherLocationUseCaseImpl
import com.denisova.impl.domain.GetWeatherLocationsUseCaseImpl
import com.denisova.impl.domain.RefreshWeatherUseCaseImpl
import com.denisova.impl.ui.DenisovaFragment
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import kotlinx.serialization.json.Json
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private const val FORECAST_BASE_URL = "https://api.open-meteo.com/"
private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"
private const val PREFS_NAME = "denisova_prefs"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { Json { ignoreUnknownKeys = true } }
                single<WeatherApiService> { HttpWeatherApiService(get()) }
                single<GeocodingApiService> { HttpGeocodingApiService(get()) }

                single {
                    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                }

                single { DenisovaStorage(get(), get()) }

                single { WeatherRepository(get(), get(), get()) }

                factory<GetWeatherLocationsUseCase> { GetWeatherLocationsUseCaseImpl(get()) }
                factory<AddWeatherLocationUseCase> { AddWeatherLocationUseCaseImpl(get()) }
                factory<RefreshWeatherUseCase> { RefreshWeatherUseCaseImpl(get()) }

                intoSetFactory(Constants.DENISOVA_SCREEN) {
                    ScreenFragmentRouter(R.string.title_denisova, DenisovaFragment::class)
                }
            }
        )
    }
}
