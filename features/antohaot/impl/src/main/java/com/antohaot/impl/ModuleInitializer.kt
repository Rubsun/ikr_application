package com.antohaot.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.antohaot.api.Constants
import com.antohaot.api.domain.usecases.AddTimeRecordUseCase
import com.antohaot.api.domain.usecases.AntohaotCurrentDateUseCase
import com.antohaot.api.domain.usecases.AntohaotElapsedTimeUseCase
import com.antohaot.api.domain.usecases.FilterTimeRecordsUseCase
import com.antohaot.impl.data.AntohaotRepository
import com.antohaot.impl.domain.AddTimeRecordUseCaseImpl
import com.antohaot.impl.domain.AntohaotCurrentDateUseCaseImpl
import com.antohaot.impl.domain.AntohaotElapsedTimeUseCaseImpl
import com.antohaot.impl.domain.FilterTimeRecordsUseCaseImpl
import com.antohaot.impl.ui.AntohaotFragment
import com.antohaot.impl.ui.AntohaotViewModel
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
                single { AntohaotRepository(context) }

                // жесткое проставление типа.
                // Нужно когда все работают с интерфейсом, а нам надо вместо него поставить имплементацию
                factory<AntohaotCurrentDateUseCase> { AntohaotCurrentDateUseCaseImpl(get()) }
                factory<AntohaotElapsedTimeUseCase> { AntohaotElapsedTimeUseCaseImpl(get()) }
                factory<FilterTimeRecordsUseCase> { FilterTimeRecordsUseCaseImpl(get()) }
                factory<AddTimeRecordUseCase> { AddTimeRecordUseCaseImpl(get()) }

                // ViewModel для фрагмента
                viewModel { AntohaotViewModel() }

                // named - квалифаер зависимости
                // Если у нас есть много поставщиков одного типа, и мы хотим их как-то разделить
                factory<Class<out Fragment>>(named(Constants.ANTOHAOT_SCREEN)) { AntohaotFragment::class.java }
            }
        )
    }
}

