package com.momuswinner.chart

import android.content.Context
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.momuswinner.chart.api.ChartConfig
import com.momuswinner.chart.api.ChartData
import com.momuswinner.chart.api.LineChartView
import com.momuswinner.chart.api.XAxisPosition

internal class LineChartViewImpl(
    context: Context
) : LineChartView {
    private val chart: LineChart = LineChart(context)

    override fun setupChart(config: ChartConfig) {
        with(chart) {
            description.isEnabled = true
            description.text = config.description
            description.textSize = config.descriptionTextSize

            setTouchEnabled(config.touchEnabled)
            setPinchZoom(config.pinchZoomEnabled)
            isDragEnabled = config.dragEnabled

            xAxis.position = when (config.xAxisPosition) {
                XAxisPosition.TOP -> XAxis.XAxisPosition.TOP
                XAxisPosition.BOTTOM -> XAxis.XAxisPosition.BOTTOM
                XAxisPosition.BOTH_SIDED -> XAxis.XAxisPosition.BOTH_SIDED
                XAxisPosition.TOP_INSIDE -> XAxis.XAxisPosition.TOP_INSIDE
                XAxisPosition.BOTTOM_INSIDE -> XAxis.XAxisPosition.BOTTOM_INSIDE
            }
            xAxis.setDrawGridLines(config.drawXGridLines)
            xAxis.gridColor = config.xGridColor
            xAxis.gridLineWidth = config.xGridLineWidth
            xAxis.textSize = config.xTextSize

            axisLeft.setDrawGridLines(config.drawLeftGridLines)
            axisLeft.gridColor = config.leftGridColor
            axisLeft.gridLineWidth = config.leftGridLineWidth
            axisLeft.textSize = config.leftTextSize
            axisRight.isEnabled = config.rightAxisEnabled

            legend.isEnabled = config.legendEnabled

            animateXY(1000, 1000)
        }
    }

    override fun updateChart(data: ChartData) {
        val entries = data.entries.map { Entry(it.x, it.y) }

        val dataSet = LineDataSet(entries, data.label).apply {
            color = data.lineColor
            valueTextColor = data.valueTextColor
            lineWidth = data.lineWidth

            setCircleColor(data.circleColor)
            circleRadius = data.circleRadius
            setDrawCircleHole(data.drawCircleHole)

            setDrawValues(data.drawValues)
            mode = when (data.mode) {
                com.momuswinner.chart.api.LineMode.LINEAR -> LineDataSet.Mode.LINEAR
                com.momuswinner.chart.api.LineMode.STEPPED -> LineDataSet.Mode.STEPPED
                com.momuswinner.chart.api.LineMode.CUBIC_BEZIER -> LineDataSet.Mode.CUBIC_BEZIER
                com.momuswinner.chart.api.LineMode.HORIZONTAL_BEZIER -> LineDataSet.Mode.HORIZONTAL_BEZIER
            }

            highLightColor = data.highlightColor
            setDrawHorizontalHighlightIndicator(data.drawHorizontalHighlightIndicator)
        }

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(data.xLabels)
        chart.data = LineData(dataSet)

        val visibleXRange = data.visibleXRangeMaximum
        if (visibleXRange != null && entries.isNotEmpty()) {
            chart.setVisibleXRangeMaximum(visibleXRange)
            chart.moveViewToX(entries.last().x)
        }

        chart.invalidate()
    }

    override fun clearChart() {
        chart.clear()
        chart.invalidate()
    }

    override fun getView(): View = chart
}

