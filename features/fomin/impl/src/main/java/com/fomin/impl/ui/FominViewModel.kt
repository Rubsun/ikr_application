package com.fomin.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.fomin.api.domain.usecases.GetBreedsUseCase
import com.fomin.impl.ui.state.FominState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

internal class FominViewModel : ViewModel() {

    private val getBreedsUseCase: GetBreedsUseCase by inject()

    private val _state = MutableStateFlow(FominState(isLoading = true))
    val state: StateFlow<FominState> = _state.asStateFlow()

    private var allBreeds: List<com.fomin.api.domain.models.CatBreed> = emptyList()
    private var loadJob: Job? = null

    init {
        loadBreeds()
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(400)
            filterBreeds(query)
        }
    }

    fun retry() {
        loadBreeds()
    }

    private fun loadBreeds() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            loadBreedsInternal()
        }
    }

    private suspend fun loadBreedsInternal() {
        _state.update { it.copy(isLoading = true, error = null) }

        val result = getBreedsUseCase()
        result
            .onSuccess { breeds ->
                allBreeds = breeds
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        breeds = breeds, 
                        error = null
                    ) 
                }
                filterBreeds(_state.value.query)
            }
            .onFailure { e ->
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        error = e.toUiMessage()
                    ) 
                }
            }
    }

    private fun filterBreeds(query: String) {
        if (allBreeds.isEmpty()) return
        val filtered = if (query.isBlank()) {
            allBreeds
        } else {
            allBreeds.filter { 
                it.name.contains(query, ignoreCase = true) ||
                it.description?.contains(query, ignoreCase = true) == true
            }
        }
        _state.update { it.copy(breeds = filtered) }
    }

    private fun Throwable.toUiMessage(): String {
        return if (this is IOException) {
            "Не удалось загрузить данные. Проверьте интернет-соединение."
        } else {
            message ?: "Не удалось загрузить данные."
        }
    }
}

