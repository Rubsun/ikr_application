package com.rubsun.chart.data

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.rubsun.chart.api.ChartConfig
import com.rubsun.chart.api.ChartData
import com.rubsun.chart.api.ChartView

class LineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LineChart(context, attrs, defStyleAttr), ChartView {

    init {
        description.isEnabled = false
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(true)
        setPinchZoom(true)
        axisRight.isEnabled = false
        legend.isEnabled = false
    }

    override fun setData(data: ChartData) {
        val entries = data.entries.map { Entry(it.x, it.y) }
        val dataSet = LineDataSet(entries, "").apply {
            color = data.lineColor
            valueTextColor = data.textColor
            lineWidth = 2f
            setCircleColor(data.lineColor)
            circleRadius = 4f
            setDrawCircleHole(false)
        }
        this.data = LineData(dataSet)
    }

    override fun setup(config: ChartConfig) {
        val xAxis = xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = config.textColor
        xAxis.setDrawGridLines(false)

        val leftAxis = axisLeft
        leftAxis.textColor = config.textColor
        leftAxis.setDrawGridLines(config.showGridLines)
    }
}
