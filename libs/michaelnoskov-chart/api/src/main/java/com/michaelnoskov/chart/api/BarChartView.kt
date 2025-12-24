package com.michaelnoskov.chart.api

/**
 * Интерфейс для работы с BarChart.
 * Скрывает детали реализации MPAndroidChart от feature модулей.
 */
interface BarChartView {
    fun setData(data: BarChartData)
    fun invalidate()
    fun setup(config: ChartConfig)
}

/**
 * Данные для BarChart
 */
data class BarChartData(
    val entries: List<BarChartEntry>,
    val barColor: Int,
    val textColor: Int
)

/**
 * Точка на BarChart
 */
data class BarChartEntry(
    val x: Float,
    val y: Float,
    val label: String? = null
)

/**
 * Конфигурация графика
 */
data class ChartConfig(
    val textColor: Int,
    val showGridLines: Boolean = true
)

