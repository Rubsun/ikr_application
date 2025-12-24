package com.momuswinner.chart

import android.content.Context
import com.momuswinner.chart.api.LineChartFactory
import com.momuswinner.chart.api.LineChartView

internal class LineChartFactoryImpl : LineChartFactory {
    override fun create(context: Any): LineChartView {
        return LineChartViewImpl(context as Context)
    }
}

