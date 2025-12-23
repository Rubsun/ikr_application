package com.nastyazz.impel.nastyazz.domain

import com.nastyazz.api.domain.usecases.ObserveItemsUseCase
import com.nastyazz.api.domain.models.Item
import com.nastyazz.impel.nastyazz.data.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class ObserveItemsUseCaseImpl(
    private val repo: ItemRepository
) : ObserveItemsUseCase {

    override fun invoke(): StateFlow<List<Item>> =
        repo.observeItems()
            .map { list -> list.map { Item(it.id, it.title, it.description, it.imageUrl) } }
            .stateIn(
                CoroutineScope(Dispatchers.Default),
                SharingStarted.Eagerly,
                emptyList()
            )
}
