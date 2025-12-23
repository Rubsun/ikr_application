package com.artemkaa.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.artemkaa.api.Constants
import com.artemkaa.api.domain.usecases.AddTimeRecordUseCase
import com.artemkaa.api.domain.usecases.ArtemkaaCurrentDateUseCase
import com.artemkaa.api.domain.usecases.ArtemkaaElapsedTimeUseCase
import com.artemkaa.api.domain.usecases.FilterTimeRecordsUseCase
import com.artemkaa.impl.data.ArtemkaaRepository
import com.artemkaa.impl.domain.AddTimeRecordUseCaseImpl
import com.artemkaa.impl.domain.ArtemkaaCurrentDateUseCaseImpl
import com.artemkaa.impl.domain.ArtemkaaElapsedTimeUseCaseImpl
import com.artemkaa.impl.domain.FilterTimeRecordsUseCaseImpl
import com.artemkaa.impl.ui.ArtemkaaFragment
import com.artemkaa.impl.ui.ArtemkaaViewModel
import com.artemkaa.network.api.TimeApiClient
import com.example.injector.AbstractInitializer
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
                // single правило - создаем инстанс один раз и всегда его отдаем
                single { ArtemkaaRepository(context, get<TimeApiClient>()) }

                // жесткое проставление типа.
                // Нужно когда все работают с интерфейсом, а нам надо вместо него поставить имплементацию
                factory<ArtemkaaCurrentDateUseCase> { ArtemkaaCurrentDateUseCaseImpl(get()) }
                factory<ArtemkaaElapsedTimeUseCase> { ArtemkaaElapsedTimeUseCaseImpl(get()) }
                factory<FilterTimeRecordsUseCase> { FilterTimeRecordsUseCaseImpl(get()) }
                factory<AddTimeRecordUseCase> { AddTimeRecordUseCaseImpl(get()) }

                // ViewModel для фрагмента
                viewModel { ArtemkaaViewModel() }

                // named - квалифаер зависимости
                // Если у нас есть много поставщиков одного типа, и мы хотим их как-то разделить
                factory<Class<out Fragment>>(named(Constants.ARTEMKAA_SCREEN)) { ArtemkaaFragment::class.java }
            }
        )
    }
}

