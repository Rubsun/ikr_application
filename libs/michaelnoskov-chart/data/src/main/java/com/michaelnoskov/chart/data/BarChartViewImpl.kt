package com.michaelnoskov.chart.data

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.michaelnoskov.chart.api.BarChartData
import com.michaelnoskov.chart.api.BarChartView
import com.michaelnoskov.chart.api.ChartConfig

/**
 * Реализация BarChartView через MPAndroidChart.
 * Инкапсулирует всю логику работы с MPAndroidChart.
 */
class BarChartViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BarChart(context, attrs, defStyleAttr), BarChartView {

    private var labels: List<String> = emptyList()

    init {
        description.isEnabled = false
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(true)
        setPinchZoom(true)
        axisRight.isEnabled = false
        legend.isEnabled = false
    }

    override fun setData(data: BarChartData) {
        val entries = data.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.y).also {
                it.data = entry.label
            }
        }
        labels = data.entries.map { it.label ?: "" }
        
        val dataSet = BarDataSet(entries, "").apply {
            color = data.barColor
            valueTextColor = data.textColor
            valueTextSize = 10f
        }
        this.data = BarData(dataSet)
        
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index >= 0 && index < labels.size) {
                    labels[index]
                } else {
                    ""
                }
            }
        }
    }

    override fun setup(config: ChartConfig) {
        val xAxis = xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = config.textColor
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.setDrawLabels(true)

        val leftAxis = axisLeft
        leftAxis.textColor = config.textColor
        leftAxis.setDrawGridLines(config.showGridLines)
    }
}

