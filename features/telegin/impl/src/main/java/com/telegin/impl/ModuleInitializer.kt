package com.telegin.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.telegin.api.Constants
import com.telegin.api.domain.usecases.GetWeatherUseCase
import com.telegin.impl.data.WeatherRepository
import com.telegin.impl.domain.GetWeatherUseCaseImpl
import com.telegin.impl.ui.TeleginFragment
import com.telegin.impl.ui.TeleginViewModel
import com.telegin.network.api.WeatherApiClient
import com.telegin.storage.api.WeatherStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                // WeatherApiClient регистрируется в libs/telegin-network/data модуле
                // WeatherStorage регистрируется в libs/telegin-storage/data модуле
                single {
                    WeatherRepository(
                        apiClient = get<WeatherApiClient>(),
                        storage = get<WeatherStorage>()
                    )
                }

                factory<GetWeatherUseCase> {
                    GetWeatherUseCaseImpl(get())
                }

                viewModel {
                    TeleginViewModel(get())
                }

                intoSetFactory(Constants.TELEGIN_SCREEN) {
                    ScreenFragmentRouter(R.string.title_telegin_screen, TeleginFragment::class)
                }
            }
        )
    }
}
