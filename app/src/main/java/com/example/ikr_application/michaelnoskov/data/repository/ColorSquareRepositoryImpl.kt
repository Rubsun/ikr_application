package com.example.ikr_application.michaelnoskov.data.repository

import com.example.ikr_application.michaelnoskov.data.datasource.LocalDataSource
import com.example.ikr_application.michaelnoskov.data.datasource.RemoteDataSource
import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData
import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class ColorSquareRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ColorSquareRepository {

    private val _searchQuery = MutableStateFlow("")

    override fun getSquareState(): Flow<SquareData> {
        return localDataSource.getSquareState()
    }

    override suspend fun updateSquareColor(color: Int) {
        val currentState = localDataSource.getSquareState().firstOrNull()
        val newState = currentState?.copy(color = color) ?: SquareData(
            id = "default",
            color = color,
            size = 200,
            rotation = 0f
        )
        localDataSource.saveSquareState(newState)
    }

    override suspend fun updateSquareRotation(rotation: Float) {
        val currentState = localDataSource.getSquareState().firstOrNull()
        val newState = currentState?.copy(rotation = rotation) ?: SquareData(
            id = "default",
            color = 0xFF6200EE.toInt(),
            size = 200,
            rotation = rotation
        )
        localDataSource.saveSquareState(newState)
    }

    override suspend fun updateSquareSize(size: Int) {
        val currentState = localDataSource.getSquareState().firstOrNull()
        val newState = currentState?.copy(size = size) ?: SquareData(
            id = "default",
            color = 0xFF6200EE.toInt(),
            size = size,
            rotation = 0f
        )
        localDataSource.saveSquareState(newState)
    }

    override fun getFilteredItems(): Flow<List<FilteredItem>> {
        // КОМБИНИРУЕМ ПОТОКИ ДАННЫХ И ПОИСКОВОГО ЗАПРОСА
        return combine(
            localDataSource.getItems(),
            _searchQuery
        ) { items, query ->
            if (query.isBlank()) {
                items
            } else {
                items.filter { item ->
                    item.text.contains(query, ignoreCase = true)
                }
            }
        }
    }

    override suspend fun addItem(text: String) {
        val item = FilteredItem(
            id = System.currentTimeMillis().toString(),
            text = text,
            timestamp = System.currentTimeMillis()
        )
        localDataSource.addItem(item)
    }

    override suspend fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    override suspend fun getChartData(): Result<List<ChartData>> {
        return remoteDataSource.fetchChartData().onFailure { error ->
            println("Failed to fetch chart data: ${error.message}")
        }
    }

    override suspend fun syncData(): Result<Unit> {
        return try {
            val localItems = localDataSource.getItems().firstOrNull() ?: emptyList()
            val lastSyncTime = localDataSource.getLastSyncTime()

            val syncResult = remoteDataSource.sync(localItems, lastSyncTime)

            syncResult.onSuccess {
                localDataSource.saveLastSyncTime(System.currentTimeMillis())
            }

            syncResult
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSquareSizes(): List<Int> {
        return listOf(150, 200, 250, 300)
    }

    // Helper extension для Flow
    private suspend fun <T> Flow<T>.firstOrNull(): T? {
        var value: T? = null
        this.collect {
            value = it
            return@collect
        }
        return value
    }
}