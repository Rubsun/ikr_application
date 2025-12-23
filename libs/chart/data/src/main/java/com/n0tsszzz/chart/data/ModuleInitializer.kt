package com.n0tsszzz.chart.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.n0tsszzz.chart.api.ChartViewFactory
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Модуль для конфигурации зависимостей chart библиотеки.
 * Регистрирует ChartViewFactory в Koin для использования в feature модулях.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<ChartViewFactory> {
                    ChartViewFactoryImpl()
                }
            }
        )
    }
}

