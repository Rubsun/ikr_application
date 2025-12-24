package com.example.ikr_application.telegin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.ikr_application.telegin.data.FakeItemRepository
import com.example.ikr_application.telegin.domain.AddItemUseCase
import com.example.ikr_application.telegin.domain.GetItemsUseCase

class ItemListViewModel : ViewModel() {

    private val repository = FakeItemRepository()
    private val getItemsUseCase = GetItemsUseCase(repository)
    private val addItemUseCase = AddItemUseCase(repository)

    private val queryFlow = MutableStateFlow("")

    private val itemsFlow = getItemsUseCase.execute()

    val state: StateFlow<ItemListState> =
        combine(itemsFlow, queryFlow) { items, query ->
            val filtered = if (query.isBlank()) {
                items
            } else {
                items.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
            }
            ItemListState(
                isLoading = false,
                query = query,
                items = filtered,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ItemListState(isLoading = true),
        )

    fun onQueryChanged(newQuery: String) {
        queryFlow.value = newQuery
    }

    fun onAddClicked(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addItemUseCase.execute(title, description)
        }
    }
}
