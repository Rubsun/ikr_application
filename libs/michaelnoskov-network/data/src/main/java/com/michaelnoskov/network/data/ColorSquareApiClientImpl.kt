package com.michaelnoskov.network.data

import android.graphics.Color
import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.network.api.ColorSquareApiClient

/**
 * Реализация ColorSquareApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 * 
 * Пока возвращает fallback данные, так как реальный API не настроен.
 */
internal class ColorSquareApiClientImpl : ColorSquareApiClient {
    
    override suspend fun getChartData(): Result<List<ChartData>> {
        // Возвращаем fallback данные, так как реальный API не настроен
        return Result.success(
            listOf(
                ChartData("Красный", 30f, Color.RED),
                ChartData("Зеленый", 25f, Color.GREEN),
                ChartData("Синий", 20f, Color.BLUE),
                ChartData("Желтый", 15f, Color.YELLOW),
                ChartData("Фиолетовый", 10f, Color.MAGENTA)
            )
        )
    }

    override suspend fun getRemoteItems(): Result<List<FilteredItem>> {
        // Возвращаем пустой список, так как реальный API не настроен
        return Result.success(emptyList())
    }

    override suspend fun syncData(localItems: List<FilteredItem>, lastSync: Long): Result<Unit> {
        // Просто возвращаем успех, так как реальный API не настроен
        return Result.success(Unit)
    }

}

