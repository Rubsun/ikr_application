package com.momuswinner.chart.api

/**
 * Данные для графика
 */
data class ChartData(
    val entries: List<ChartEntry>,
    val xLabels: List<String>,
    val label: String,
    val lineColor: Int,
    val valueTextColor: Int,
    val lineWidth: Float,
    val circleColor: Int,
    val circleRadius: Float,
    val drawCircleHole: Boolean = false,
    val drawValues: Boolean = false,
    val mode: LineMode = LineMode.LINEAR,
    val highlightColor: Int,
    val drawHorizontalHighlightIndicator: Boolean = false,
    val visibleXRangeMaximum: Float? = null
)

data class ChartEntry(
    val x: Float,
    val y: Float
)

enum class LineMode {
    LINEAR, STEPPED, CUBIC_BEZIER, HORIZONTAL_BEZIER
}

