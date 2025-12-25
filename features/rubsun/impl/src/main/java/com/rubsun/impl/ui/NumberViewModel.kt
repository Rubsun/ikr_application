package com.rubsun.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubsun.api.domain.models.NumberDisplayModel
import com.rubsun.api.domain.usecases.GetNumberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal data class UiState(
    val numbers: List<NumberDisplayModel>,
    val randomNumber: NumberDisplayModel?,
    val searchQuery: String,
    val isLoading: Boolean,
)

internal class NumberViewModel(
    private val getNumberUseCase: GetNumberUseCase
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")
    private val randomNumber = MutableStateFlow<NumberDisplayModel?>(null)
    private val isLoading = MutableStateFlow(false)

    val state: StateFlow<UiState> = combine(
        getNumberUseCase.getAllNumbers(),
        searchQuery,
        randomNumber,
        isLoading,
    ) { numbers, query, random, loading ->
        val filtered = if (query.isBlank()) {
            numbers
        } else {
            numbers.filter {
                it.value.toString().contains(query, ignoreCase = true) ||
                    it.label.contains(query, ignoreCase = true) ||
                    it.squared.toString().contains(query, ignoreCase = true)
            }
        }
        UiState(
            numbers = filtered,
            randomNumber = random,
            searchQuery = query,
            isLoading = loading,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState(emptyList(), null, "", false)
    )

    init {
        loadRandomNumber()
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun loadRandomNumber() {
        viewModelScope.launch {
            isLoading.value = true
            randomNumber.value = getNumberUseCase.getRandomNumber()
            isLoading.value = false
        }
    }

    fun addNumber(value: Int, label: String) {
        viewModelScope.launch {
            isLoading.value = true
            getNumberUseCase.addNumber(value, label)
            isLoading.value = false
        }
    }

    fun addNumberWithFactFromApi(value: Int) {
        viewModelScope.launch {
            isLoading.value = true
            getNumberUseCase.addNumberWithFactFromApi(value)
            isLoading.value = false
        }
    }

    fun clearAllNumbers() {
        viewModelScope.launch {
            isLoading.value = true
            getNumberUseCase.clearAllNumbers()
            isLoading.value = false
        }
    }
}


