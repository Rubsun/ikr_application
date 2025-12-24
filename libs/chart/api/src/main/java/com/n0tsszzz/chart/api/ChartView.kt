package com.n0tsszzz.chart.api

/**
 * Абстракция для работы с графиками.
 * Скрывает детали реализации MPAndroidChart от feature модулей.
 */
interface ChartView {
    fun setData(data: ChartData)
    fun invalidate()
    fun setup(config: ChartConfig)
}

/**
 * Данные для графика
 */
data class ChartData(
    val entries: List<ChartEntry>,
    val lineColor: Int,
    val textColor: Int
)

/**
 * Точка на графике
 */
data class ChartEntry(
    val x: Float,
    val y: Float
)

/**
 * Конфигурация графика
 */
data class ChartConfig(
    val textColor: Int,
    val showGridLines: Boolean = true
)

