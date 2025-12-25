package com.rubsun.chart.api

interface ChartView {
    fun setData(data: ChartData)
    fun invalidate()
    fun setup(config: ChartConfig)
}

data class ChartData(
    val entries: List<ChartEntry>,
    val lineColor: Int,
    val textColor: Int
)

data class ChartEntry(
    val x: Float,
    val y: Float
)

data class ChartConfig(
    val textColor: Int,
    val showGridLines: Boolean = true
)
