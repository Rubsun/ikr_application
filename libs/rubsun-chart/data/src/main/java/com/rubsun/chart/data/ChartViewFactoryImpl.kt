package com.rubsun.chart.data

import android.content.Context
import android.view.View
import com.rubsun.chart.api.ChartViewFactory

internal class ChartViewFactoryImpl : ChartViewFactory {
    override fun create(context: Context): View {
        return LineChartView(context)
    }
}
