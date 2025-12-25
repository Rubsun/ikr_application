package com.michaelnoskov.chart.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.michaelnoskov.chart.api.BarChartViewFactory
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Модуль для конфигурации зависимостей chart библиотеки для michaelnoskov.
 * Регистрирует BarChartViewFactory в Koin для использования в feature модулях.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<BarChartViewFactory> {
                    BarChartViewFactoryImpl()
                }
            }
        )
    }
}

