package com.grigoran.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.grigoran.api.domain.AddItemUseCase
import com.grigoran.api.domain.FilterItemUseCase
import com.grigoran.api.domain.GetItemUseCase
import com.grigoran.api.domain.SortItemUseCase

import com.grigoran.impl.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class ExampleUiState(
    val items: List<ItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val minPriceFilter: Double = 0.0
)

internal class ExampleViewModel : ViewModel() {

    private val getItems: GetItemUseCase by inject()
    private val addItem: AddItemUseCase by inject()
    private val filterItem: FilterItemUseCase by inject()
    private val sortedItem: SortItemUseCase by inject()

    private val _uiState = MutableStateFlow(ExampleUiState())
    private val minPriceFilter = MutableStateFlow(0.0)
    val uiState: StateFlow<ExampleUiState> = _uiState

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            getItems()
                .combine(minPriceFilter) { items, min ->
                    items.filter { it.price >= min }
                }
                .collect { filtered ->
                    _uiState.update {
                        it.copy(
                            items = filtered.map { item ->
                                ItemUi(
                                    id = item.id,
                                    displayTitle = item.title,
                                    displayPrice = "${item.price} $",
                                    imageUrt = "https://picsum.photos/200?random=${item.id}"
                                )
                            }
                        )
                    }
                }
        }
    }

    fun onAddClicked(title: String, price: Int) {
        addItem(title, price)
    }

    fun onMinPriceChanged(price: Double) {
        minPriceFilter.value = price
    }
}