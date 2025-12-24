package com.example.ikr_application.michaelnoskov.data.datasource

import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LocalDataSource {
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
}