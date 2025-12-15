package com.example.ikr_application.nastyazz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.nastyazz.domain.AddItemUseCase
import com.example.ikr_application.nastyazz.domain.ObserveItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemsViewModel(
    observeItemsUseCase: ObserveItemsUseCase,
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")

    val state: StateFlow<ItemsState> =
        combine(
            observeItemsUseCase(),
            query
        ) { items, q ->
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