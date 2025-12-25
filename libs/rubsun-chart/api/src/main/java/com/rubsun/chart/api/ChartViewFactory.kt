package com.rubsun.chart.api

import android.content.Context
import android.view.View

interface ChartViewFactory {
    fun create(context: Context): View
}
