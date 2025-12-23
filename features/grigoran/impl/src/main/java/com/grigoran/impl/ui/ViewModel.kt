package com.grigoran.impl.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.grigoran.api.domain.AddItemUseCase
import com.grigoran.api.domain.ItemSearchUseCase
import com.grigoran.api.domain.ItemSuggestUseCase
import com.grigoran.api.domain.SortItemUseCase
import com.grigoran.api.models.Item

import com.grigoran.impl.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

internal data class ExampleUiState(
    val items: List<ItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val minPriceFilter: Double = 0.0
)

internal class ExampleViewModel : ViewModel() {


    private val sortedItem: SortItemUseCase by inject()
    private val searchUseCase: ItemSearchUseCase by inject()
    private val suggestUseCase: ItemSuggestUseCase by inject()

    private val queryFlow = MutableStateFlow<String?>(null)
    private val _sortAscending = MutableStateFlow(true)

    private val state = combine(queryFlow.filterNotNull(), _sortAscending) { query, ascending ->
        query to ascending
    }.debounce(500.milliseconds)
        .distinctUntilChanged()
        .flatMapLatest { (query, ascending) ->
            flow {
                emit(State(query, isPending = true))
                val result = searchUseCase(query)
                val sortedItems = sortedItem(result.items, ascending)
                emit(
                    State(
                        query = result.query,
                        isPending = false,
                        data = sortedItems,
                        error = result.error
                    )
                )
            }
        }
        .onStart {
            emit(State(query = suggestUseCase() ?: "", isPending = false))
        }

    fun state(): Flow<State> = state
    fun search(query: String) {queryFlow.value = query}
    fun setSortAscending(ascending: Boolean) {
        Log.d("Setsoert","Sort")
        _sortAscending.value = ascending
    }
    val sortAscending: Boolean get() = _sortAscending.value


    data class State(
        val query: String,
        val isPending: Boolean,
        val data: List<Item> = emptyList(),
        val error: Throwable? = null
    )
}
