package com.michaelnoskov.impl.data.datasource

import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.model.SquareData
import kotlinx.coroutines.flow.Flow

internal interface LocalDataSource {
    // Square
    fun getSquareState(): Flow<SquareData>
    suspend fun saveSquareState(squareData: SquareData)

    // Items
    fun getItems(): Flow<List<FilteredItem>>
    fun searchItems(query: String): Flow<List<FilteredItem>>
    suspend fun saveItems(items: List<FilteredItem>)
    suspend fun addItem(item: FilteredItem)
    suspend fun deleteItem(id: String)

    // Preferences
    suspend fun saveLastSyncTime(timestamp: Long)
    suspend fun getLastSyncTime(): Long
    
    // Temperature History
    fun getTemperatureHistory(): Flow<List<com.michaelnoskov.api.domain.repository.TemperaturePoint>>
    suspend fun addTemperaturePoint(point: com.michaelnoskov.api.domain.repository.TemperaturePoint)
}

