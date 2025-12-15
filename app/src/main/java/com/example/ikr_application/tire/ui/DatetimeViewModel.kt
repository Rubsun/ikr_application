package com.example.ikr_application.tire.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.tire.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DatetimeViewModel : ViewModel() {

    private val currentDateUseCase = CurrentDateUseCase()
    private val elapsedTimeUseCase = ElapsedTimeUseCase()
    private val getDevicesUseCase = GetDevicesUseCase()
    private val addDeviceUseCase = AddDeviceUseCase()

    private val _uiState = MutableStateFlow(DatetimeState())
    val uiState: StateFlow<DatetimeState> = _uiState.asStateFlow()

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val date = currentDateUseCase()
            val elapsed = elapsedTimeUseCase(_uiState.value.selectedPrecision)
            val devices = getDevicesUseCase("")

            _uiState.update {
                it.copy(
                    dateText = dateFormatter.format(date),
                    elapsedText = formatElapsedTime(elapsed, it.selectedPrecision),
                    devices = devices,
                    isLoading = false
                )
            }
        }
    }

    fun onPrecisionSelected(precision: TimePrecisions) {
        viewModelScope.launch {
            val elapsed = elapsedTimeUseCase(precision)
            _uiState.update {
                it.copy(
                    selectedPrecision = precision,
                    elapsedText = formatElapsedTime(elapsed, precision)
                )
            }
        }
    }

    fun onFilterChanged(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(filterQuery = query, isLoading = true) }

            val filteredDevices = getDevicesUseCase(query)

            _uiState.update {
                it.copy(devices = filteredDevices, isLoading = false)
            }
        }
    }

    fun onAddDevice(deviceName: String) {
        viewModelScope.launch {
            if (deviceName.isNotBlank()) {
                _uiState.update { it.copy(isLoading = true) }

                addDeviceUseCase(deviceName)
                val devices = getDevicesUseCase(_uiState.value.filterQuery)

                _uiState.update {
                    it.copy(devices = devices, isLoading = false)
                }
            }
        }
    }

    private fun formatElapsedTime(value: Long, precision: TimePrecisions): String {
        return "$value ${precision.typeName}"
    }
}
