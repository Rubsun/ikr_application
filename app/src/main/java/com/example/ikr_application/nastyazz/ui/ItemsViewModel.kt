package com.example.ikr_application.nastyazz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.nastyazz.domain.GetItemByIdUseCase
import com.example.ikr_application.nastyazz.domain.GetItemsUseCase
import com.example.ikr_application.nastyazz.domain.Item

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val items: List<Item>) : UiState()
    data class Error(val message: String) : UiState()
}

class ItemsViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    init { loadItems() }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                _state.value = UiState.Loading
                val items = getItemsUseCase()
                _state.value = UiState.Success(items)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}