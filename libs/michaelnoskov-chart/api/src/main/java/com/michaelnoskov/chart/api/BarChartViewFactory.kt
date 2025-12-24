package com.michaelnoskov.chart.api

import android.content.Context
import android.view.View

/**
 * Фабрика для создания BarChartView.
 * Реализация находится в data модуле.
 */
interface BarChartViewFactory {
    /**
     * Создает экземпляр BarChartView.
     * @param context контекст для создания View
     * @return View, который реализует BarChartView интерфейс
     */
    fun create(context: Context): View
}

