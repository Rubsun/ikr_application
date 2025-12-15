package com.example.ikr_application.grigoran.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.grigoran.domain.AddItemUseCase
import com.example.ikr_application.grigoran.domain.ItemUseCases
import com.example.ikr_application.grigoran.data.Repository
import com.example.ikr_application.grigoran.domain.FilterItemsUseCase
import com.example.ikr_application.grigoran.domain.SortItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExampleUiState(
    val items: List<ItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val minPriceFilter: Double = 0.0
)

class ExampleViewModel : ViewModel() {

    private val repo = Repository()
    private val getItems = ItemUseCases(repo)
    private val addItem = AddItemUseCase(repo)
    private val filterItem = FilterItemsUseCase()
    private val sortedItem = SortItemsUseCase()

    private val _uiState = MutableStateFlow(ExampleUiState())
    private val minPriceFilter = MutableStateFlow(0.0)
    val uiState: StateFlow<ExampleUiState> = _uiState

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            combine(
                getItems(),
                minPriceFilter
            ) { domainItems, minPrice ->

                val sorted = sortedItem(domainItems)
                val filtered = filterItem(sorted, minPrice)

                filtered.map { d ->
                    ItemUi(
                        id = d.id,
                        displayTitle = d.title,
                        displayPrice = "${d.price} $",
                        imageUrt = "https://picsum.photos/200?random=${d.id}"
                    )
                }
            }.collect { uiItems ->
                _uiState.update {
                    it.copy(items = uiItems)
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