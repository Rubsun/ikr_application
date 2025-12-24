package com.denisova.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisova.api.domain.models.WeatherLocation
import com.denisova.api.domain.usecases.AddWeatherLocationUseCase
import com.denisova.api.domain.usecases.GetWeatherLocationsUseCase
import com.denisova.api.domain.usecases.RefreshWeatherUseCase
import com.example.injector.inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal data class UiState(
    val locations: List<WeatherLocation>,
    val query: String,
    val isLoading: Boolean,
    val error: String?,
)

internal class DenisovaViewModel : ViewModel() {
    private val getWeatherLocationsUseCase: GetWeatherLocationsUseCase by inject()
    private val refreshWeatherUseCase: RefreshWeatherUseCase by inject()
    private val addWeatherLocationUseCase: AddWeatherLocationUseCase by inject()

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
