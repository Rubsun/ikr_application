package com.example.ikr_application.michaelnoskov.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(
    private val context: Context
) : LocalDataSource {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences("color_square_prefs", Context.MODE_PRIVATE)
    }

    // Храним в памяти
    private val _squareState = MutableStateFlow(
        SquareData(
            id = "default",
            color = 0xFF6200EE.toInt(),
            size = 200,
            rotation = 0f,
            alpha = 1f
        )
    )

    private val _items = MutableStateFlow<List<FilteredItem>>(emptyList())

    init {
        // Инициализируем начальными данными
        _items.value = listOf(
            FilteredItem(
                id = "1",
                text = "Красный квадрат",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "2",
                text = "Зеленый треугольник",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "3",
                text = "Синий круг",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "4",
                text = "Желтый прямоугольник",
                timestamp = System.currentTimeMillis()
            ),
            FilteredItem(
                id = "5",
                text = "Фиолетовый овал",
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override fun getSquareState(): Flow<SquareData> = _squareState

    override suspend fun saveSquareState(squareData: SquareData) {
        _squareState.value = squareData
    }

    override fun getItems(): Flow<List<FilteredItem>> = _items

    // ДОБАВИЛИ РЕАЛИЗАЦИЮ ПОИСКА
    override fun searchItems(query: String): Flow<List<FilteredItem>> {
        return _items.map { items ->
            if (query.isBlank()) {
                items
            } else {
                items.filter { item ->
                    item.text.contains(query, ignoreCase = true)
                }
            }
        }
    }

    override suspend fun saveItems(items: List<FilteredItem>) {
        _items.value = items
    }

    override suspend fun addItem(item: FilteredItem) {
        _items.value = _items.value + item
    }

    override suspend fun deleteItem(id: String) {
        _items.value = _items.value.filter { it.id != id }
    }

    override suspend fun saveLastSyncTime(timestamp: Long) {
        preferences.edit().putLong("last_sync_time", timestamp).apply()
    }

    override suspend fun getLastSyncTime(): Long {
        return preferences.getLong("last_sync_time", 0L)
    }
}