package com.example.ikr_application.michaelnoskov.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.michaelnoskov.data.api.MockColorSquareApi
import com.example.ikr_application.michaelnoskov.data.datasource.LocalDataSourceImpl
import com.example.ikr_application.michaelnoskov.data.datasource.RemoteDataSourceImpl
import com.example.ikr_application.michaelnoskov.data.repository.ColorSquareRepositoryImpl
import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData
import com.example.ikr_application.michaelnoskov.domain.usecase.AddItemUseCase
import com.example.ikr_application.michaelnoskov.domain.usecase.GetChartDataUseCase
import com.example.ikr_application.michaelnoskov.domain.usecase.GetFilteredItemsUseCase
import com.example.ikr_application.michaelnoskov.domain.usecase.GetSquareStateUseCase
import com.example.ikr_application.michaelnoskov.domain.usecase.SyncDataUseCase
import com.example.ikr_application.michaelnoskov.domain.usecase.UpdateSearchUseCase
import com.example.ikr_application.michaelnoskov.domain.usecase.UpdateSquareUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ColorSquareViewModel(application: Application) : AndroidViewModel(application) {

    // Создаем все зависимости вручную
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Используйте моковый URL или реальный
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: com.example.ikr_application.michaelnoskov.data.api.ColorSquareApi by lazy {
//        retrofit.create(com.example.ikr_application.michaelnoskov.data.api.ColorSquareApi::class.java)
        MockColorSquareApi()
    }

    private val localDataSource: LocalDataSourceImpl by lazy {
        LocalDataSourceImpl(application)
    }

    private val remoteDataSource: RemoteDataSourceImpl by lazy {
        RemoteDataSourceImpl(api, com.example.ikr_application.michaelnoskov.data.mapper.NetworkMapper())
    }

    private val repository: ColorSquareRepositoryImpl by lazy {
        ColorSquareRepositoryImpl(localDataSource, remoteDataSource)
    }

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

    private val colors = listOf(
        0xFFE53935.toInt(), // Красный
        0xFF43A047.toInt(), // Зеленый
        0xFF1E88E5.toInt(), // Синий
        0xFFFB8C00.toInt(), // Оранжевый
        0xFF8E24AA.toInt()  // Фиолетовый
    )

    private val sizes = listOf(150, 200, 250, 300)
    private var currentSizeIndex = 1

    init {
        // Подписываемся на данные
        combine(
            getSquareStateUseCase(),
            getFilteredItemsUseCase()
        ) { squareState, filteredItems ->
            ColorSquareState(
                squareState = squareState,
                filteredItems = filteredItems,
                itemsCount = filteredItems.size
            )
        }.onEach { newState ->
            _state.update {
                it.copy(
                    squareState = newState.squareState,
                    filteredItems = newState.filteredItems,
                    itemsCount = newState.itemsCount
                )
            }
        }.launchIn(viewModelScope)

        // Загружаем данные графика (асинхронно)
        viewModelScope.launch {
            loadChartData()
        }
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
        // Получаем текущий цвет
        val currentColor = state.value.squareState.color
        val currentIndex = colors.indexOf(currentColor)
        val nextIndex = if (currentIndex == -1) 0 else (currentIndex + 1) % colors.size
        val nextColor = colors[nextIndex]

        viewModelScope.launch {  // Оставьте launch здесь, но это отдельная строка
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

            val result = syncDataUseCase()
            result.onSuccess {
                _state.update { it.copy(
                    isLoading = false,
                    error = null
                ) }
                // Перезагружаем данные после синхронизации
                loadChartData()
            }.onFailure { error ->
                _state.update { it.copy(
                    isLoading = false,
                    error = error.message ?: "Ошибка синхронизации"
                ) }
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
}
