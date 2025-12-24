package com.akiko23.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.akiko23.api.Constants
import com.akiko23.impl.data.DeviceRepository
import com.akiko23.impl.data.models.Akiko23State
import com.akiko23.impl.ui.Akiko23Fragment
import com.akiko23.impl.ui.Akiko23ViewModel
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Модуль для конфигурации зависимостей akiko23.
 * Его надо добавлять в манифест, чтобы KOIN + startup их собрали.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        // Добавление в KOIN модулей
        loadKoinModules(
            // Создаем и добавляем модуль
            module {
                // Repository
                single { DeviceRepository(get()) }

                // Персистентное хранилище для состояния
                single<PrimitiveStorage<Akiko23State>> {
                    val factory: PrimitiveStorage.Factory = get()
                    factory.create(
                        name = "akiko23_quote_state",
                        serializer = Akiko23State.serializer()
                    )
                }

                // ViewModel для фрагмента
                viewModel {
                    Akiko23ViewModel(
                        repository = get(),
                        storage = get()
                    )
                }

                // Fragment для навигации
                factory<Class<out Fragment>>(named(Constants.AKIKO23_SCREEN)) {
                    Akiko23Fragment::class.java
                }
            }
        )
    }
}

