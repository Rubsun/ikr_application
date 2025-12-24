package com.example.impl.ui

import androidx.lifecycle.ViewModel
import com.example.demyanenko.impl.ui.DemyanenkoItem
import com.example.impl.domain.GetF1CarUseCase
import com.example.libs.demyanenkoopenf1.DemyanenkoOpenF1Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

internal class F1CarViewModel(
    private val getF1CarUseCase: GetF1CarUseCase,
    private val openF1Repository: DemyanenkoOpenF1Repository
) : ViewModel() {

    private val _state = MutableStateFlow(F1CarState())
    val state = _state.asStateFlow()

    init {
        loadDrivers()
        observeF1Cars("")
    }

    private fun loadDrivers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                openF1Repository.getDrivers().collect { drivers ->
                    val driverItems = drivers.map { DemyanenkoItem.DriverItem(it) }
                    _state.update {
                        it.copy(drivers = driverItems)
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    fun searchF1Cars(query: String) {
        _state.update { it.copy(searchQuery = query) }
        observeF1Cars(query)
    }

    private fun observeF1Cars(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            getF1CarUseCase.getF1Cars(query).collect { cars ->
                val carItems = cars.map { DemyanenkoItem.CarItem(it) }
                _state.update { it.copy(cars = carItems) }
            }
        }
    }

    fun addF1Car(name: String, sound: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.update { it.copy(isLoading = true) }
                val newCar = getF1CarUseCase.addF1Car(name, sound)
                val carItem = DemyanenkoItem.CarItem(newCar)
                _state.update { it.copy(cars = it.cars + carItem, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}

