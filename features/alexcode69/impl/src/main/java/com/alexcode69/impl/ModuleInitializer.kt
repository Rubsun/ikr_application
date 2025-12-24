package com.alexcode69.impl

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.alexcode69.api.Constants
import com.alexcode69.api.domain.usecases.AddTimeEntryUseCase
import com.alexcode69.api.domain.usecases.CurrentDateUseCase
import com.alexcode69.api.domain.usecases.ElapsedTimeUseCase
import com.alexcode69.api.domain.usecases.FetchRequestInfoUseCase
import com.alexcode69.api.domain.usecases.SearchTimeEntriesUseCase
import com.alexcode69.impl.data.Alexcode69ApiService
import com.alexcode69.impl.data.DeviceRepository
import com.example.network.api.RetrofitServiceFactory
import com.alexcode69.impl.domain.AddTimeEntryUseCaseImpl
import com.alexcode69.impl.domain.CurrentDateUseCaseImpl
import com.alexcode69.impl.domain.ElapsedTimeUseCaseImpl
import com.alexcode69.impl.domain.FetchRequestInfoUseCaseImpl
import com.alexcode69.impl.domain.SearchTimeEntriesUseCaseImpl
import com.alexcode69.impl.ui.Alexcode69Fragment
import com.alexcode69.impl.ui.MyViewModel
import com.example.injector.AbstractInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Модуль для конфигурации зависимостей
 * Его надо добавлять в манифест, что бы KOIN + startup их собрали
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        // Добавление в KOIN модулей
        loadKoinModules(
            // Создаем и добавляем модуль
            module {
                single<SharedPreferences> {
                    androidContext().getSharedPreferences("alexcode69_prefs", Context.MODE_PRIVATE)
                }

                single<Alexcode69ApiService> {
                    get<RetrofitServiceFactory>().create(
                        "https://httpbin.org/",
                        Alexcode69ApiService::class.java
                    )
                }
                
                // single правило - создаем инстанс один раз и всегда его отдаем
                single { DeviceRepository(androidContext(), get()) }

                // factory правило - все время создаем новый инстанс
                // жесткое проставление типа.
                // Нужно когда все работают с интерфейсом, а нам надо вместо него поставить имплементацию
                factory<CurrentDateUseCase> { CurrentDateUseCaseImpl(get()) }
                factory<ElapsedTimeUseCase> { ElapsedTimeUseCaseImpl(get()) }
                factory<SearchTimeEntriesUseCase> { SearchTimeEntriesUseCaseImpl(get()) }
                factory<AddTimeEntryUseCase> { AddTimeEntryUseCaseImpl(get()) }
                factory<FetchRequestInfoUseCase> { FetchRequestInfoUseCaseImpl(get()) }

                viewModel { MyViewModel(get(), get(), get(), get()) }

                // named - квалифаер зависимости
                // Если у нас есть много поставщиков одного типа, и мы хотим их как-то разделить
                factory<Class<out Fragment>>(named(Constants.ALEXCODE69_SCREEN)) {
                    Alexcode69Fragment::class.java
                }
            }
        )
    }
}

