package com.example.ikr_application.rubsun.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.rubsun.domain.GetNumberUseCase
import com.example.ikr_application.rubsun.domain.models.NumberDisplayModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiState(
    val numbers: List<NumberDisplayModel>,
    val randomNumber: NumberDisplayModel?,
    val searchQuery: String,
    val isLoading: Boolean,
)

class NumberViewModel : ViewModel() {
    private val getNumberUseCase = GetNumberUseCase()

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
}
