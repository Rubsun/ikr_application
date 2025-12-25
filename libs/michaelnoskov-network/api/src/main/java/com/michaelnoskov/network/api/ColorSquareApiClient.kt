package com.michaelnoskov.network.api

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem

/**
 * Клиент для работы с ColorSquare API.
 * Абстракция для получения данных из внешнего источника.
 */
interface ColorSquareApiClient {
    suspend fun getChartData(): Result<List<ChartData>>
    suspend fun getRemoteItems(): Result<List<FilteredItem>>
    suspend fun syncData(localItems: List<FilteredItem>, lastSync: Long): Result<Unit>
}

