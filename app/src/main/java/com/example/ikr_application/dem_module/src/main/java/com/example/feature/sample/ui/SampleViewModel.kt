package com.example.ikr_application.dem_module.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.dem_module.data.SampleRepository
import com.example.ikr_application.dem_module.domain.GetItemsUseCase
import com.example.ikr_application.dem_module.domain.SearchItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SampleViewModel : ViewModel() {

    // Инициализация (в реальном проекте через DI - Hilt/Dagger)
    private val repository = SampleRepository()
    private val getItemsUseCase = GetItemsUseCase(repository)
    private val searchItemsUseCase = SearchItemsUseCase(repository)

    private val _uiState = MutableStateFlow<SampleUiState>(SampleUiState.Loading)
    val uiState: StateFlow<SampleUiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _uiState.value = SampleUiState.Loading
            try {
                val items = getItemsUseCase()
                _uiState.value = if (items.isEmpty()) {
                    SampleUiState.Empty
                } else {
                    SampleUiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = SampleUiState.Error(
                    e.message ?: "Неизвестная ошибка"
                )
            }
        }
    }

    fun searchItems(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                loadItems()
                return@launch
            }

            _uiState.value = SampleUiState.Loading
            try {
                val items = searchItemsUseCase(query)
                _uiState.value = if (items.isEmpty()) {
                    SampleUiState.Empty
                } else {
                    SampleUiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = SampleUiState.Error(
                    e.message ?: "Ошибка поиска"
                )
            }
        }
    }
}
