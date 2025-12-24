package com.momuswinner.chart.api

/**
 * Конфигурация графика
 */
data class ChartConfig(
    val description: String,
    val descriptionTextSize: Float,
    val touchEnabled: Boolean = true,
    val pinchZoomEnabled: Boolean = true,
    val dragEnabled: Boolean = true,
    val xAxisPosition: XAxisPosition = XAxisPosition.BOTTOM,
    val drawXGridLines: Boolean = true,
    val xGridColor: Int,
    val xGridLineWidth: Float,
    val xTextSize: Float,
    val drawLeftGridLines: Boolean = true,
    val leftGridColor: Int,
    val leftGridLineWidth: Float,
    val leftTextSize: Float,
    val rightAxisEnabled: Boolean = false,
    val legendEnabled: Boolean = false
)

enum class XAxisPosition {
    TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE, BOTTOM_INSIDE
}

