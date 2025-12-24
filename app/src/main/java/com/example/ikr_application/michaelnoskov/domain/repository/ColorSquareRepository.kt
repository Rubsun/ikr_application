package com.example.ikr_application.michaelnoskov.domain.repository

import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData
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