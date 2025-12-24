package com.momuswinner.chart

import android.content.Context
import com.example.injector.AbstractInitializer
import com.momuswinner.chart.api.LineChartFactory
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<LineChartFactory> { LineChartFactoryImpl() }
            }
        )
    }
}

