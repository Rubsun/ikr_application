package com.example.ikr_application.eremin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.eremin.data.CapybaraRepository
import com.example.ikr_application.eremin.domain.GetCapybarasUseCase
import com.example.ikr_application.eremin.domain.models.Capybara
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class CapybaraState(
    val capybaras: List<Capybara> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)

class CapybaraViewModel : ViewModel() {

    private val repository = CapybaraRepository()
    private val getCapybarasUseCase = GetCapybarasUseCase(repository)

    private val _uiState = MutableStateFlow(CapybaraState())
    val uiState: StateFlow<CapybaraState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10

    fun loadNextPage() {
        viewModelScope.launch {
            getCapybarasUseCase.execute(currentPage * pageSize, pageSize)
                .flowOn(Dispatchers.IO)
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .catch { e -> _uiState.value = _uiState.value.copy(error = e.message, isLoading = false) }
                .collect { newCapybaras ->
                    val currentCapybaras = _uiState.value.capybaras
                    _uiState.value = _uiState.value.copy(
                        capybaras = currentCapybaras + newCapybaras,
                        isLoading = false
                    )
                    currentPage++
                }
        }
    }
}
