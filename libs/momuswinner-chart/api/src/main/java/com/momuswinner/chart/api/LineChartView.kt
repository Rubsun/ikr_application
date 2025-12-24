package com.momuswinner.chart.api

/**
 * Интерфейс для работы с линейным графиком
 */
interface LineChartView {
    /**
     * Настройка графика
     */
    fun setupChart(config: ChartConfig)

    /**
     * Обновление данных графика
     */
    fun updateChart(data: ChartData)

    /**
     * Очистка графика
     */
    fun clearChart()

    /**
     * Получение View для использования в layout
     * Возвращает Android View (в JVM модуле используется Any для совместимости)
     */
    fun getView(): Any
}

