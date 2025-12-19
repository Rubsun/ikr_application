package com.example.ikr_application.michaelnoskov.data

import android.content.Context
import com.example.ikr_application.R
import com.example.ikr_application.michaelnoskov.data.models.FilteredItem
import com.example.ikr_application.michaelnoskov.data.models.SquareState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Date

class ColorSquareRepository(private val context: Context? = null) {

    // Состояние квадрата
    private val _squareState = MutableStateFlow(
        SquareState(
            color = 0xFF6200EE.toInt(),
            size = 200, // Будет переопределено из ресурсов
            rotation = 0f,
            alpha = 1f
        )
    )
    val squareState: StateFlow<SquareState> = _squareState

    // Список элементов
    private val _items = MutableStateFlow<List<FilteredItem>>(emptyList())
    private val items: StateFlow<List<FilteredItem>> = _items

    // Фильтр поиска
    private val _searchQuery = MutableStateFlow("")

    // Отфильтрованные элементы
    val filteredItems: Flow<List<FilteredItem>> = combine(
        items,
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

    init {
        // Начальные данные
        val initialItems = listOf(
            FilteredItem(1, "Красный квадрат", Date().time),
            FilteredItem(2, "Зеленый треугольник", Date().time),
            FilteredItem(3, "Синий круг", Date().time),
            FilteredItem(4, "Желтый прямоугольник", Date().time),
            FilteredItem(5, "Фиолетовый овал", Date().time)
        )
        _items.value = initialItems

        // Если есть контекст, устанавливаем начальный размер из ресурсов
        context?.let {
            val initialSize = it.resources.getDimensionPixelSize(R.dimen.michaelnoskov_square_size_medium)
            _squareState.value = _squareState.value.copy(size = initialSize)
        }
    }

    fun updateSquareColor(color: Int) {
        _squareState.value = _squareState.value.copy(color = color)
    }

    fun updateSquareRotation(rotation: Float) {
        _squareState.value = _squareState.value.copy(rotation = rotation)
    }

    fun updateSquareSize(size: Int) {
        _squareState.value = _squareState.value.copy(size = size)
    }

    fun addItem(text: String) {
        val newItem = FilteredItem(
            id = Date().time,
            text = text,
            timestamp = Date().time
        )
        _items.value = _items.value + newItem
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getChartData(): List<Pair<String, Float>> {
        return listOf(
            "Красный" to 30f,
            "Зеленый" to 25f,
            "Синий" to 20f,
            "Желтый" to 15f,
            "Фиолетовый" to 10f
        )
    }
}