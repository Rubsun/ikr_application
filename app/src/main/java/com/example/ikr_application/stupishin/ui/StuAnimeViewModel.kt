package com.example.ikr_application.stupishin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.stupishin.domain.GetTopAnimeUseCase
import com.example.ikr_application.stupishin.domain.SearchAnimeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class StuAnimeViewModel : ViewModel() {
    private val topUseCase = GetTopAnimeUseCase()
    private val searchUseCase = SearchAnimeUseCase()

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

        try {
            val items = topUseCase.execute(page = 1)
            _state.update { it.copy(isLoading = false, items = items, error = null) }
        } catch (e: Exception) {
            val message = if (e is IOException) {
                "Не удалось загрузить данные. Проверь интернет и VPN."
            } else {
                e.message ?: "Не удалось загрузить данные."
            }

            _state.update { it.copy(isLoading = false, error = message) }
        }
    }

    private suspend fun searchInternal(query: String) {
        _state.update { it.copy(isLoading = true, error = null) }

        try {
            val items = searchUseCase.execute(query = query)
            _state.update { it.copy(isLoading = false, items = items, error = null) }
        } catch (e: Exception) {
            val message = if (e is IOException) {
                "Не удалось загрузить данные. Проверь интернет и VPN."
            } else {
                e.message ?: "Не удалось загрузить данные."
            }

            _state.update { it.copy(isLoading = false, error = message) }
        }
    }
}
