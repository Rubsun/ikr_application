package com.michaelnoskov.impl.ui.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.repository.ColorSquareRepository
import com.michaelnoskov.api.domain.repository.TemperaturePoint
import com.michaelnoskov.impl.domain.usecase.AddItemUseCase
import com.michaelnoskov.impl.domain.usecase.GetChartDataUseCase
import com.michaelnoskov.impl.domain.usecase.GetFilteredItemsUseCase
import com.michaelnoskov.impl.domain.usecase.GetSquareStateUseCase
import com.michaelnoskov.impl.domain.usecase.SyncDataUseCase
import com.michaelnoskov.impl.domain.usecase.UpdateSearchUseCase
import com.michaelnoskov.impl.domain.usecase.UpdateSquareUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ColorSquareViewModel(
    application: Application,
    private val repository: ColorSquareRepository
) : AndroidViewModel(application) {

    // Use Cases
    private val getSquareStateUseCase = GetSquareStateUseCase(repository)
    private val getFilteredItemsUseCase = GetFilteredItemsUseCase(repository)
    private val updateSquareUseCase = UpdateSquareUseCase(repository)
    private val addItemUseCase = AddItemUseCase(repository)
    private val updateSearchUseCase = UpdateSearchUseCase(repository)
    private val getChartDataUseCase = GetChartDataUseCase(repository)
    private val syncDataUseCase = SyncDataUseCase(repository)

    private val _state = MutableStateFlow(ColorSquareState())
    val state: StateFlow<ColorSquareState> = _state

    private val sizes = listOf(150, 200, 250, 300)
    private var currentSizeIndex = 1
    
    // Диапазон температур для градиента (от -20°C до 40°C)
    private val minTemp = -20.0
    private val maxTemp = 40.0

    init {
        // Подписываемся на данные
        combine(
            getSquareStateUseCase(),
            getFilteredItemsUseCase(),
            repository.getTemperatureHistory()
        ) { squareState, filteredItems, temperatureHistory ->
            ColorSquareState(
                squareState = squareState,
                filteredItems = filteredItems,
                itemsCount = filteredItems.size,
                temperatureHistory = temperatureHistory
            )
        }.onEach { newState ->
            _state.update {
                it.copy(
                    squareState = newState.squareState,
                    filteredItems = newState.filteredItems,
                    itemsCount = newState.itemsCount,
                    temperatureHistory = newState.temperatureHistory
                )
            }
        }.launchIn(viewModelScope)

        // График теперь показывает историю температур, загружать chartData не нужно
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
        // Получаем текущий цвет и меняем на следующий из дискретного набора
        val currentColor = state.value.squareState.color
        // Используем простой набор цветов для ручного изменения
        val colors = listOf(
            0xFFE53935.toInt(), // Красный
            0xFF43A047.toInt(), // Зеленый
            0xFF1E88E5.toInt(), // Синий
            0xFFFB8C00.toInt(), // Оранжевый
            0xFF8E24AA.toInt()  // Фиолетовый
        )
        val currentIndex = colors.indexOf(currentColor)
        val nextIndex = if (currentIndex == -1) 0 else (currentIndex + 1) % colors.size
        val nextColor = colors[nextIndex]

        viewModelScope.launch {
            updateSquareUseCase(color = nextColor)
        }
    }

    fun rotateSquare() {
        val currentRotation = state.value.squareState.rotation
        val newRotation = (currentRotation + 45f) % 360f

        viewModelScope.launch {
            updateSquareUseCase(rotation = newRotation)
        }
    }

    fun resizeSquare() {
        currentSizeIndex = (currentSizeIndex + 1) % sizes.size
        val newSize = sizes[currentSizeIndex]

        viewModelScope.launch {
            updateSquareUseCase(size = newSize)
        }
    }

    fun syncData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Загружаем погоду и обновляем цвет квадрата
            val weatherResult = repository.getWeatherTemperature()
            weatherResult.onSuccess { temperature ->
                val color = temperatureToColor(temperature)
                updateSquareUseCase(color = color)
                
                // Сохраняем точку температуры в историю
                repository.addTemperaturePoint(
                    TemperaturePoint(temperature, System.currentTimeMillis())
                )
                
                _state.update { it.copy(currentTemperature = temperature) }
            }.onFailure { error ->
                _state.update { it.copy(error = error.message ?: "Ошибка загрузки погоды") }
            }

            val result = syncDataUseCase()
            result.onSuccess {
                _state.update { it.copy(
                    isLoading = false,
                    error = null
                ) }
            }.onFailure { error ->
                _state.update { it.copy(
                    isLoading = false,
                    error = error.message ?: "Ошибка синхронизации"
                ) }
            }
            
            // Обновляем состояние загрузки после завершения всех операций
            if (weatherResult.isFailure && result.isFailure) {
                _state.update { it.copy(isLoading = false) }
            } else if (weatherResult.isSuccess && result.isSuccess) {
                _state.update { it.copy(isLoading = false, error = null) }
            }
        }
    }

    private suspend fun loadChartData() {
        val result = getChartDataUseCase()
        result.onSuccess { chartData ->
            _state.update { it.copy(
                chartData = chartData,
                error = null
            ) }
        }.onFailure { error ->
            // Fallback данные
            _state.update { it.copy(
                chartData = getFallbackChartData(),
                error = "Используются локальные данные"
            ) }
        }
    }

    private fun getFallbackChartData(): List<ChartData> {
        return listOf(
            ChartData("Красный", 30f, android.graphics.Color.RED),
            ChartData("Зеленый", 25f, android.graphics.Color.GREEN),
            ChartData("Синий", 20f, android.graphics.Color.BLUE),
            ChartData("Желтый", 15f, android.graphics.Color.YELLOW),
            ChartData("Фиолетовый", 10f, android.graphics.Color.MAGENTA)
        )
    }
    
    
    /**
     * Преобразует температуру в цвет используя градиент:
     * Холодно (-20°C и ниже) -> Синий
     * Тепло (40°C и выше) -> Красный
     * Между ними -> плавный переход через цвета
     */
    private fun temperatureToColor(temperature: Double): Int {
        // Ограничиваем температуру диапазоном
        val clampedTemp = temperature.coerceIn(minTemp, maxTemp)
        
        // Нормализуем температуру в диапазон [0, 1]
        val normalized = (clampedTemp - minTemp) / (maxTemp - minTemp)
        
        // Определяем цвета для градиента
        // Холодно: синий (0, 0, 255)
        // Средне: зеленый (0, 255, 0) 
        // Жарко: красный (255, 0, 0)
        
        val red: Int
        val green: Int
        val blue: Int
        
        if (normalized < 0.5) {
            // От синего к зеленому (0.0 -> 0.5)
            val t = normalized * 2.0 // [0, 1]
            red = 0
            green = (t * 255).toInt()
            blue = ((1 - t) * 255).toInt()
        } else {
            // От зеленого к красному (0.5 -> 1.0)
            val t = (normalized - 0.5) * 2.0 // [0, 1]
            red = (t * 255).toInt()
            green = ((1 - t) * 255).toInt()
            blue = 0
        }
        
        return Color.rgb(red, green, blue)
    }
}

