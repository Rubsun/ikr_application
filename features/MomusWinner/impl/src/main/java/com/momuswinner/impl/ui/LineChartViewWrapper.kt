package com.momuswinner.impl.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.momuswinner.chart.api.LineChartView

/**
 * Кастомный View для использования в layout файлах
 * Использует интерфейс из api модуля
 */
class LineChartViewWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var chartView: LineChartView? = null

    fun setChartView(chartView: LineChartView) {
        if (this.chartView == null) {
            this.chartView = chartView
            addView(chartView.getView() as View)
        }
    }

    fun getChartView(): LineChartView? = chartView
}

