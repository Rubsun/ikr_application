package com.tire.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tire.api.domain.TimePrecisions
import com.tire.impl.data.DeviceRepository
import com.tire.impl.domain.*
import com.tire.api.domain.models.DeviceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.Locale

internal class DatetimeViewModel : ViewModel(), KoinComponent {

    private val repository: DeviceRepository by inject()
    private val currentDateUseCase: CurrentDateUseCase by inject()
    private val elapsedTimeUseCase: ElapsedTimeUseCase by inject()
    private val getDevicesUseCase: GetDevicesUseCase by inject()
    private val addDeviceUseCase: AddDeviceUseCase by inject()

    private val _uiState = MutableStateFlow(DatetimeState())
    val uiState: StateFlow<DatetimeState> = _uiState.asStateFlow()

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.initialize()

            val savedPrecision = repository.loadLastPrecision()
                ?.let { TimePrecisions.fromName(it) }
                ?: TimePrecisions.MS

            val date = currentDateUseCase()
            val elapsed = elapsedTimeUseCase(savedPrecision)
            val devices = getDevicesUseCase("")

            _uiState.update {
                it.copy(
                    dateText = dateFormatter.format(date),
                    elapsedText = formatElapsedTime(elapsed, savedPrecision),
                    selectedPrecision = savedPrecision,
                    devices = devices,
                    isLoading = false
                )
            }
        }
    }

    fun onPrecisionSelected(precision: TimePrecisions) {
        viewModelScope.launch {
            val elapsed = elapsedTimeUseCase(precision)
            repository.saveLastPrecision(precision.name)
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
