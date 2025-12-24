package com.michaelnoskov.api.domain.repository

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.model.SquareData
import kotlinx.coroutines.flow.Flow

interface ColorSquareRepository {
    // Квадрат
    fun getSquareState(): Flow<SquareData>
    suspend fun updateSquareColor(color: Int)
    suspend fun updateSquareRotation(rotation: Float)
    suspend fun updateSquareSize(size: Int)

    // Элементы
    fun getFilteredItems(): Flow<List<FilteredItem>>
    suspend fun addItem(text: String)
    suspend fun updateSearchQuery(query: String)

    // График (сетевые данные)
    suspend fun getChartData(): Result<List<ChartData>>

    // Синхронизация
    suspend fun syncData(): Result<Unit>

    // ДОБАВЛЯЕМ НОВЫЙ МЕТОД
    suspend fun getSquareSizes(): List<Int>
}

