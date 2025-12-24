package com.michaelnoskov.impl.data.repository

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.model.SquareData
import com.michaelnoskov.api.domain.repository.ColorSquareRepository
import com.michaelnoskov.api.domain.repository.TemperaturePoint
import com.michaelnoskov.impl.data.datasource.LocalDataSource
import com.michaelnoskov.impl.data.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull

internal class ColorSquareRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ColorSquareRepository {

    private val _searchQuery = MutableStateFlow("")

    override fun getSquareState(): Flow<SquareData> {
        return localDataSource.getSquareState()
    }

    override suspend fun updateSquareColor(color: Int) {
        val currentState = localDataSource.getSquareState().firstOrNull()
        currentState?.let {
            val newState = it.copy(color = color)
            localDataSource.saveSquareState(newState)
        }
    }

    override suspend fun updateSquareRotation(rotation: Float) {
        val currentState = localDataSource.getSquareState().firstOrNull()
        currentState?.let {
            val newState = it.copy(rotation = rotation)
            localDataSource.saveSquareState(newState)
        }
    }

    override suspend fun updateSquareSize(size: Int) {
        val currentState = localDataSource.getSquareState().firstOrNull()
        currentState?.let {
            val newState = it.copy(size = size)
            localDataSource.saveSquareState(newState)
        }
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

    override suspend fun getWeatherTemperature(): Result<Double> {
        return remoteDataSource.fetchWeatherTemperature()
    }

    override fun getTemperatureHistory(): Flow<List<TemperaturePoint>> {
        return localDataSource.getTemperatureHistory()
    }

    override suspend fun addTemperaturePoint(point: TemperaturePoint) {
        localDataSource.addTemperaturePoint(point)
    }
}

