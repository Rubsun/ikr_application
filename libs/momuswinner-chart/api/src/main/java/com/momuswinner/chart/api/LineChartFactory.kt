package com.momuswinner.chart.api

/**
 * Фабрика для создания LineChartView
 * Context передается как Any для совместимости с JVM модулем
 */
interface LineChartFactory {
    fun create(context: Any): LineChartView
}

