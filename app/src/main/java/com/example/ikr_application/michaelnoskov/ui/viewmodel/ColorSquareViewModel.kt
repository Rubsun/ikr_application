package com.example.ikr_application.michaelnoskov.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.R
import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository
import com.example.ikr_application.michaelnoskov.domain.AddItemUseCase
import com.example.ikr_application.michaelnoskov.domain.GetChartDataUseCase
import com.example.ikr_application.michaelnoskov.domain.GetFilteredItemsUseCase
import com.example.ikr_application.michaelnoskov.domain.GetSquareStateUseCase
import com.example.ikr_application.michaelnoskov.domain.UpdateSearchUseCase
import com.example.ikr_application.michaelnoskov.domain.UpdateSquareUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ColorSquareViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ColorSquareRepository()
    private val context: Context = application.applicationContext

    private val getSquareStateUseCase = GetSquareStateUseCase(repository)
    private val getFilteredItemsUseCase = GetFilteredItemsUseCase(repository)
    private val updateSquareUseCase = UpdateSquareUseCase(repository)
    private val addItemUseCase = AddItemUseCase(repository)
    private val updateSearchUseCase = UpdateSearchUseCase(repository)
    private val getChartDataUseCase = GetChartDataUseCase(repository)

    private val _state = MutableStateFlow(ColorSquareState())
    val state: StateFlow<ColorSquareState> = _state

    // Размеры квадрата из ресурсов
    private val squareSizes = listOf(
        R.dimen.michaelnoskov_square_size_small,
        R.dimen.michaelnoskov_square_size_medium,
        R.dimen.michaelnoskov_square_size_large,
        R.dimen.michaelnoskov_square_size_xlarge
    )

    // Индекс текущего размера
    private var currentSizeIndex = 1 // Начинаем со среднего размера

    init {
        // Инициализируем начальный размер из ресурсов
        val initialSize = context.resources.getDimensionPixelSize(squareSizes[currentSizeIndex])
        _state.value = _state.value.copy(
            squareState = _state.value.squareState.copy(size = initialSize)
        )

        // Комбинируем все Flow в один State
        combine(
            getSquareStateUseCase(),
            getFilteredItemsUseCase()
        ) { squareState, filteredItems ->
            _state.value.copy(
                squareState = squareState.copy(size = _state.value.squareState.size),
                filteredItems = filteredItems,
                itemsCount = filteredItems.size,
                chartData = getChartDataUseCase()
            )
        }.onEach { newState ->
            _state.value = newState
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            updateSearchUseCase(query)
            _state.update { it.copy(searchQuery = query) }
        }
    }

    fun onAddItemClicked(text: String) {
        if (text.isNotBlank()) {
            viewModelScope.launch {
                addItemUseCase(text)
            }
        }
    }

    fun changeSquareColor() {
        val colors = listOf(
            0xFFE53935.toInt(), // Красный
            0xFF43A047.toInt(), // Зеленый
            0xFF1E88E5.toInt(), // Синий
            0xFFFB8C00.toInt(), // Оранжевый
            0xFF8E24AA.toInt()  // Фиолетовый
        )
        val currentColor = _state.value.squareState.color
        val currentIndex = colors.indexOf(currentColor)
        val nextIndex = if (currentIndex == -1) 0 else (currentIndex + 1) % colors.size
        val nextColor = colors[nextIndex]

        viewModelScope.launch {
            updateSquareUseCase(color = nextColor)
        }
    }

    fun rotateSquare() {
        val currentRotation = _state.value.squareState.rotation
        val newRotation = (currentRotation + 45f) % 360f

        viewModelScope.launch {
            updateSquareUseCase(rotation = newRotation)
        }
    }

    fun resizeSquare() {
        // Переходим к следующему размеру
        currentSizeIndex = (currentSizeIndex + 1) % squareSizes.size

        // Получаем новый размер из ресурсов
        val newSize = context.resources.getDimensionPixelSize(squareSizes[currentSizeIndex])

        viewModelScope.launch {
            updateSquareUseCase(size = newSize)
        }
    }
}