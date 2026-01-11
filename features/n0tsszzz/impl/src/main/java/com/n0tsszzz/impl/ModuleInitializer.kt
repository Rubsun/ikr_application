package com.n0tsszzz.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.n0tsszzz.api.Constants
import com.n0tsszzz.api.domain.usecases.AddTimeRecordUseCase
import com.n0tsszzz.api.domain.usecases.GetTimeRecordsUseCase
import com.n0tsszzz.api.domain.usecases.MarkoCurrentDateUseCase
import com.n0tsszzz.api.domain.usecases.MarkoElapsedTimeUseCase
import com.n0tsszzz.impl.data.MarkoRepository
import com.n0tsszzz.impl.domain.AddTimeRecordUseCaseImpl
import com.n0tsszzz.impl.domain.GetTimeRecordsUseCaseImpl
import com.n0tsszzz.impl.domain.MarkoCurrentDateUseCaseImpl
import com.n0tsszzz.impl.domain.MarkoElapsedTimeUseCaseImpl
import com.n0tsszzz.impl.ui.MarkoFragment
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
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
                    androidContext().getSharedPreferences("n0tsszzz_prefs", Context.MODE_PRIVATE)
                }
                // Json уже зарегистрирован в network модуле, используем его через DI
                // single правило - создаем инстанс один раз и всегда его отдаем
                single { MarkoRepository(get(), get(), get<Json>()) }

                // factory правило - все время создаем новый инстанс
                factory<MarkoCurrentDateUseCase> { MarkoCurrentDateUseCaseImpl(get()) }
                factory<MarkoElapsedTimeUseCase> { MarkoElapsedTimeUseCaseImpl(get()) }
                factory<GetTimeRecordsUseCase> { GetTimeRecordsUseCaseImpl(get()) }
                factory<AddTimeRecordUseCase> { AddTimeRecordUseCaseImpl(get()) }

                // named - квалифаер зависимости
                // Если у нас есть много поставщиков одного типа, и мы хотим их как-то разделить
                intoSetFactory(Constants.N0TSSZZZ_SCREEN) {
                    ScreenFragmentRouter(R.string.n0tsszzz_title, MarkoFragment::class)
                }
            }
        )
    }
}

