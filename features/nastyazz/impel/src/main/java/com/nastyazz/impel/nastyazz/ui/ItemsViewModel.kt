package com.nastyazz.impel.nastyazz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.nastyazz.api.domain.usecases.AddItemUseCase
import com.nastyazz.api.domain.usecases.ItemSearchUseCase
import com.nastyazz.api.domain.usecases.ItemSuggestUseCase
import com.nastyazz.api.domain.usecases.ObserveItemsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class ItemsViewModel(
    private val searchUseCase: ItemSearchUseCase,
    private val suggestUseCase: ItemSuggestUseCase
) : ViewModel() {

    private val queryFlow = MutableStateFlow<String?>(null)

    val state = queryFlow
        .filterNotNull()
        .debounce(500)
        .flatMapLatest { query ->
            flow {
                emit(ItemsState(isLoading = true, query = query))
                val items = searchUseCase(query)
                emit(ItemsState(items = items, query = query))
            }
        }
        .onStart {
            emit(
                ItemsState(
                    query = suggestUseCase() ?: ""
                )
            )
        }

    fun search(query: String) {queryFlow.value = query}

//    val state: StateFlow<ItemsState> = combine(observeItemsUseCase(), query) { items, q ->
//        ItemsState(
//            items = items.filter { it.title.contains(q, ignoreCase = true) },
//            query = q
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ItemsState(isLoading = true))
//
//    fun onSearch(text: String) {
//        query.value = text
//    }
//
//    fun onAddClicked(title: String) {
//        viewModelScope.launch {
//            addItemUseCase(title)
//            query.value = ""
//        }
//    }
}