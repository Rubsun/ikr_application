package com.example.impl.ui

import androidx.lifecycle.ViewModel
import com.example.impl.domain.GetF1CarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

internal class F1CarViewModel(private val getF1CarUseCase: GetF1CarUseCase) : ViewModel() {
    private val _state = MutableStateFlow(F1CarState())
    val state = _state.asStateFlow()

    init {
        observeF1Cars("")
    }

    fun searchF1Cars(query: String) {
        _state.update { it.copy(searchQuery = query) }
        observeF1Cars(query)
    }

    private fun observeF1Cars(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            getF1CarUseCase.getF1Cars(query).collect { cars ->
                _state.update { it.copy(cars = cars) }
            }
        }
    }

    fun addF1Car(name: String, sound: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.update { it.copy(isLoading = true) }
                val newCar = getF1CarUseCase.addF1Car(name, sound)
                _state.update { it.copy(cars = it.cars + newCar, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
