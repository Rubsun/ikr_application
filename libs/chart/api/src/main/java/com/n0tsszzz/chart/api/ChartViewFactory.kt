package com.n0tsszzz.chart.api

import android.content.Context
import android.view.View

/**
 * Фабрика для создания ChartView.
 * Реализация находится в data модуле.
 */
interface ChartViewFactory {
    /**
     * Создает экземпляр ChartView.
     * @param context контекст для создания View
     * @return View, который реализует ChartView интерфейс
     */
    fun create(context: Context): View
}

