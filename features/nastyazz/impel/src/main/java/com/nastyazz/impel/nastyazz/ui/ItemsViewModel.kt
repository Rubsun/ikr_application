package com.nastyazz.impel.nastyazz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.nastyazz.api.domain.usecases.AddItemUseCase
import com.nastyazz.api.domain.usecases.ObserveItemsUseCase
import kotlinx.coroutines.flow.*

internal class ItemsViewModel : ViewModel() {

    private val observeItemsUseCase: ObserveItemsUseCase by inject()
    private val addItemUseCase: AddItemUseCase by inject()

    private val query = MutableStateFlow("")

    val state: StateFlow<ItemsState> =
        combine(observeItemsUseCase(), query) { items, q ->
            ItemsState(
                items = items.filter {
                    it.title.contains(q, ignoreCase = true)
                },
                query = q
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ItemsState(isLoading = true)
        )

    fun onSearch(text: String) {
        query.value = text
    }

    fun onAddClicked(title: String) {
        viewModelScope.launch {
            addItemUseCase(title)
            query.value = ""
        }
    }
}
