package com.vtyapkova.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtyapkova.api.domain.models.ViktoriaDisplayModel
import com.vtyapkova.api.domain.usecases.AddViktoriaFromApiUseCase
import com.vtyapkova.api.domain.usecases.AddViktoriaUseCase
import com.vtyapkova.api.domain.usecases.GetMultipleViktoriaUseCase
import com.vtyapkova.api.domain.usecases.GetRandomViktoriaUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal data class ViktoriaUiState(
    val randomViktoria: ViktoriaDisplayModel? = null,
    val viktoriaList: List<ViktoriaDisplayModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

internal class ViktoriaViewModel(
    private val getRandomViktoriaUseCase: GetRandomViktoriaUseCase,
    private val getMultipleViktoriaUseCase: GetMultipleViktoriaUseCase,
    private val addViktoriaUseCase: AddViktoriaUseCase,
    private val addViktoriaFromApiUseCase: AddViktoriaFromApiUseCase,
    private val repository: ViktoriaRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _randomViktoria = MutableStateFlow<ViktoriaDisplayModel?>(null)
    private val _allData = MutableStateFlow<List<ViktoriaDisplayModel>>(emptyList())

    // Flow для списка данных с учетом фильтрации
    private val filteredListFlow = combine(_allData, _searchQuery) { allData, query ->
        if (query.isBlank()) {
            allData
        } else {
            val lowerQuery = query.lowercase()
            allData.filter {
                it.displayViktoria.lowercase().contains(lowerQuery) ||
                it.shortViktoria.lowercase().contains(lowerQuery) ||
                it.initials.lowercase().contains(lowerQuery)
            }
        }
    }

    // Объединяем все Flow в единый State
    val uiState: StateFlow<ViktoriaUiState> = combine(
        _randomViktoria,
        filteredListFlow,
        _searchQuery,
        _isLoading,
        _error
    ) { randomViktoria, viktoriaList, searchQuery, isLoading, error ->
        ViktoriaUiState(
            randomViktoria = randomViktoria,
            viktoriaList = viktoriaList,
            searchQuery = searchQuery,
            isLoading = isLoading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = ViktoriaUiState(isLoading = true)
    )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            // Инициализируем репозиторий
            repository.initialize()

            getRandomViktoriaUseCase.execute()
                .catch { e ->
                    _error.value = e.message ?: "Unknown error"
                }
                .onEach { randomViktoria ->
                    _randomViktoria.value = randomViktoria
                }
                .launchIn(viewModelScope)

            // Подписываемся на изменения данных из репозитория
            getMultipleViktoriaUseCase.execute()
                .catch { e ->
                    _error.value = e.message ?: "Unknown error"
                    _isLoading.value = false
                }
                .onEach { data ->
                    _allData.value = data
                    _isLoading.value = false
                }
                .launchIn(viewModelScope)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addViktoria(firstName: String, lastName: String) {
        if (firstName.isBlank() || lastName.isBlank()) {
            _error.value = "Имя и фамилия не могут быть пустыми"
            return
        }

        viewModelScope.launch {
            _error.value = null
            try {
                addViktoriaUseCase.execute(firstName, lastName)
                    .catch { e ->
                        _error.value = e.message ?: "Error adding viktoria"
                    }
                    .collect {
                        // После добавления обновляем все данные
                        val updatedData = getMultipleViktoriaUseCase.execute().first()
                        _allData.value = updatedData
                    }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error adding viktoria"
            }
        }
    }

    fun refreshRandomViktoria() {
        viewModelScope.launch {
            _error.value = null
            getRandomViktoriaUseCase.execute()
                .catch { e ->
                    _error.value = e.message ?: "Error loading random viktoria"
                }
                .onEach { randomViktoria ->
                    _randomViktoria.value = randomViktoria
                }
                .launchIn(viewModelScope)
        }
    }

    fun addViktoriaFromApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                addViktoriaFromApiUseCase.execute()
                    .catch { e ->
                        _error.value = e.message ?: "Error loading from API"
                        _isLoading.value = false
                    }
                    .collect {
                        // После добавления обновляем все данные
                        val updatedData = getMultipleViktoriaUseCase.execute().first()
                        _allData.value = updatedData
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error loading from API"
                _isLoading.value = false
            }
        }
    }
}

