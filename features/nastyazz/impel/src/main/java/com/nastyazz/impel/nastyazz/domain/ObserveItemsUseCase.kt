package com.nastyazz.impel.nastyazz.domain

import com.nastyazz.impel.nastyazz.data.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class ObserveItemsUseCase(
    private val repo: ItemRepository
) {
    operator fun invoke(): StateFlow<List<Item>> =
        repo.observeItems().map { list ->
            list.map {
                Item(it.id, it.title, it.description, it.imageUrl)
            }
        }
            .stateIn(
                CoroutineScope(Dispatchers.Default),
                SharingStarted.Eagerly,
                emptyList()
            )
}