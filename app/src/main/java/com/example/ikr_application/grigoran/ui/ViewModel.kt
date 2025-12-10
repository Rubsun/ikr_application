package com.example.ikr_application.grigoran.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.grigoran.data.Repository
import com.example.ikr_application.grigoran.domain.FilterItemsUseCase
import com.example.ikr_application.grigoran.domain.ItemUseCases
import com.example.ikr_application.grigoran.domain.SortItemsUseCase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExampleViewModel : ViewModel() {

    private val repository = Repository()
    private val getItemsUseCase = ItemUseCases(repository)
    private val filterItemsUseCase = FilterItemsUseCase()
    private val sortItemsUseCase = SortItemsUseCase()

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState(isLoading = true)
                var domainItems = getItemsUseCase()
                domainItems = filterItemsUseCase(items = domainItems, minPrice = 2.0)
                domainItems = sortItemsUseCase(items = domainItems, ascending = true)
                val uiItems = domainItems.map { it.toUi() }
                _uiState.value = UiState(isLoading = false, items = uiItems)
            } catch (e: Exception) {
                _uiState.value = UiState(isLoading = false, items = emptyList(), error = e.message)
            }
        }
    }
}