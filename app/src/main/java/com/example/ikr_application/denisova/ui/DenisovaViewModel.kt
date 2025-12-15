package com.example.ikr_application.denisova.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.denisova.domain.AddWeatherLocationUseCase
import com.example.ikr_application.denisova.domain.GetWeatherLocationsUseCase
import com.example.ikr_application.denisova.domain.RefreshWeatherUseCase
import com.example.ikr_application.denisova.domain.models.WeatherLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiState(
    val locations: List<WeatherLocation>,
    val query: String,
    val isLoading: Boolean,
    val error: String?,
)

class DenisovaViewModel : ViewModel() {
    private val getWeatherLocationsUseCase = GetWeatherLocationsUseCase()
    private val refreshWeatherUseCase = RefreshWeatherUseCase()
    private val addWeatherLocationUseCase = AddWeatherLocationUseCase()

    private val query = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)

    init {
        refresh()
    }

    val state: StateFlow<UiState> = combine(
        getWeatherLocationsUseCase.execute(),
        query,
        isLoading,
        error,
    ) { list, q, loading, err ->
        val filtered = if (q.isBlank()) {
            list
        } else {
            list.filter {
                it.name.contains(q, ignoreCase = true) ||
                    (it.admin1?.contains(q, ignoreCase = true) == true) ||
                    (it.country?.contains(q, ignoreCase = true) == true) ||
                    it.latitude.toString().contains(q) ||
                    it.longitude.toString().contains(q) ||
                    it.time.contains(q, ignoreCase = true) ||
                    it.temperatureC.toString().contains(q) ||
                    it.id.toString().contains(q)
            }
        }
        UiState(
            locations = filtered,
            query = q,
            isLoading = loading,
            error = err,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState(emptyList(), "", false, null)
    )

    fun onQueryChanged(text: String) {
        query.value = text
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            runCatching {
                refreshWeatherUseCase.execute()
            }.onFailure { t ->
                error.value = t.message
            }

            isLoading.value = false
        }
    }

    fun onAddCity(cityName: String) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            runCatching {
                addWeatherLocationUseCase.execute(cityName = cityName)
            }.onFailure { t ->
                error.value = t.message
            }

            isLoading.value = false
        }
    }
}
