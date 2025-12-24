package com.grigoran.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.grigoran.api.domain.ItemSearchUseCase
import com.grigoran.api.domain.ItemSuggestUseCase
import com.grigoran.api.domain.SortItemUseCase
import com.grigoran.api.models.Item
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ExampleViewModel : ViewModel() {

    private val sortItemsUseCase: SortItemUseCase by inject()
    private val searchUseCase: ItemSearchUseCase by inject()
    private val suggestUseCase: ItemSuggestUseCase by inject()

    private var sortAscending = true
    private var loadJob: Job? = null

    private val _state = MutableStateFlow(
        State(
            query = "",
            isPending = true
        )
    )
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        loadInitial()
    }

    fun search(query: String) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(500)
            searchInternal(query)
        }
    }

    fun toggleSort() {
        sortAscending = !sortAscending
        val currentQuery = _state.value.query
        if (currentQuery.isNotBlank()) {
            search(currentQuery)
        }
    }

    private fun loadInitial() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val query = suggestUseCase().orEmpty()
            _state.value = State(
                query = query,
                isPending = false
            )
            if (query.isNotBlank()) {
                searchInternal(query)
            }
        }
    }

    private suspend fun searchInternal(query: String) {
        _state.value = _state.value.copy(isPending = true)

        val result = searchUseCase(query)
        val sortedItems = sortItemsUseCase(result.items, sortAscending)

        _state.value = State(
            query = query,
            isPending = false,
            data = sortedItems,
            error = result.error
        )
    }

    internal data class State(
        val query: String,
        val isPending: Boolean,
        val data: List<Item> = emptyList(),
        val error: Throwable? = null
    )
}
