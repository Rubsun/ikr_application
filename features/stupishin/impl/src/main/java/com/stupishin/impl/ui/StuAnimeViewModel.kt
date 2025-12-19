package com.stupishin.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.stupishin.api.domain.usecases.GetTopAnimeUseCase
import com.stupishin.api.domain.usecases.SearchAnimeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

internal class StuAnimeViewModel : ViewModel() {

    private val topUseCase: GetTopAnimeUseCase by inject()
    private val searchUseCase: SearchAnimeUseCase by inject()

    private val _state = MutableStateFlow(StuAnimeState(isLoading = true))
    val state: StateFlow<StuAnimeState> = _state.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadTop()
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(400)
            if (query.isBlank()) {
                loadTopInternal()
            } else {
                searchInternal(query)
            }
        }
    }

    fun retry() {
        val q = state.value.query
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            if (q.isBlank()) {
                loadTopInternal()
            } else {
                searchInternal(q)
            }
        }
    }

    private fun loadTop() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            loadTopInternal()
        }
    }

    private suspend fun loadTopInternal() {
        _state.update { it.copy(isLoading = true, error = null) }

        val result = topUseCase()
        result
            .onSuccess { items ->
                _state.update { it.copy(isLoading = false, items = items, error = null) }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.toUiMessage()) }
            }
    }

    private suspend fun searchInternal(query: String) {
        _state.update { it.copy(isLoading = true, error = null) }

        val result = searchUseCase(query)
        result
            .onSuccess { items ->
                _state.update { it.copy(isLoading = false, items = items, error = null) }
            }
            .onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.toUiMessage()) }
            }
    }

    private fun Throwable.toUiMessage(): String {
        return if (this is IOException) {
            "Не удалось загрузить данные. Проверь интернет и VPN."
        } else {
            message ?: "Не удалось загрузить данные."
        }
    }
}
