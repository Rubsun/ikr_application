package com.michaelnoskov.chart.data

import android.content.Context
import android.view.View
import com.michaelnoskov.chart.api.BarChartViewFactory

/**
 * Реализация BarChartViewFactory.
 * Создает BarChartViewImpl из data модуля.
 */
internal class BarChartViewFactoryImpl : BarChartViewFactory {
    override fun create(context: Context): View {
        return BarChartViewImpl(context)
    }
}

