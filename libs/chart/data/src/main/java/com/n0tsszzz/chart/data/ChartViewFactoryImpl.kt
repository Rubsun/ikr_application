package com.n0tsszzz.chart.data

import android.content.Context
import android.view.View
import com.n0tsszzz.chart.api.ChartViewFactory

/**
 * Реализация ChartViewFactory.
 * Создает LineChartView из data модуля.
 */
internal class ChartViewFactoryImpl : ChartViewFactory {
    override fun create(context: Context): View {
        return LineChartView(context)
    }
}

