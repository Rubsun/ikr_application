package com.vtyapkova.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.vtyapkova.api.Constants
import com.vtyapkova.api.domain.usecases.AddViktoriaFromApiUseCase
import com.vtyapkova.api.domain.usecases.AddViktoriaUseCase
import com.vtyapkova.api.domain.usecases.GetMultipleViktoriaUseCase
import com.vtyapkova.api.domain.usecases.GetRandomViktoriaUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import com.vtyapkova.impl.data.storage.ViktoriaStorage
import com.vtyapkova.impl.domain.AddViktoriaFromApiUseCaseImpl
import com.vtyapkova.impl.domain.AddViktoriaUseCaseImpl
import com.vtyapkova.impl.domain.GetMultipleViktoriaUseCaseImpl
import com.vtyapkova.impl.domain.GetRandomViktoriaUseCaseImpl
import com.vtyapkova.impl.ui.ViktoriaFragment
import com.vtyapkova.impl.ui.ViktoriaViewModel
import com.vtyapkova.network.api.RandomUserApiClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private const val PREFS_NAME = "vtyapkova_prefs"

/**
 * Модуль для конфигурации зависимостей vtyapkova.
 * Его надо добавлять в манифест, чтобы KOIN + startup их собрали.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        // Добавление в KOIN модулей
        loadKoinModules(
            // Создаем и добавляем модуль
            module {
                // Storage
                single {
                    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                }

                single {
                    ViktoriaStorage(get())
                }

                // Repository
                single {
                    ViktoriaRepository(
                        storage = get(),
                        apiClient = get<RandomUserApiClient>()
                    )
                }

                // Use cases
                factory<GetRandomViktoriaUseCase> {
                    GetRandomViktoriaUseCaseImpl(get())
                }

                factory<GetMultipleViktoriaUseCase> {
                    GetMultipleViktoriaUseCaseImpl(get())
                }

                factory<AddViktoriaUseCase> {
                    AddViktoriaUseCaseImpl(get())
                }

                factory<AddViktoriaFromApiUseCase> {
                    AddViktoriaFromApiUseCaseImpl(get())
                }

                // ViewModel для фрагмента
                viewModel {
                    ViktoriaViewModel(
                        getRandomViktoriaUseCase = get(),
                        getMultipleViktoriaUseCase = get(),
                        addViktoriaUseCase = get(),
                        addViktoriaFromApiUseCase = get(),
                        repository = get()
                    )
                }

                // Fragment для навигации
                intoSetFactory(Constants.VTYAPKOVA_SCREEN) {
                    ScreenFragmentRouter(R.string.title_viktoria, ViktoriaFragment::class)
                }
            }
        )
    }
}

