package com.rubsun.chart.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.rubsun.chart.api.ChartViewFactory
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                factory<ChartViewFactory> { ChartViewFactoryImpl() }
            }
        )
    }
}
