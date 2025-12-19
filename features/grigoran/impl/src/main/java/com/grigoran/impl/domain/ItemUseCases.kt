package com.grigoran.impl.domain

import com.grigoran.api.domain.AddItemUseCase
import com.grigoran.api.domain.FilterItemUseCase
import com.grigoran.api.domain.GetItemUseCase
import com.grigoran.api.domain.SortItemUseCase
import com.grigoran.api.models.Item
import com.grigoran.impl.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class ItemUseCases (
    private val repository: Repository
) : GetItemUseCase {
    override fun invoke() = repository.observeItems().map { dtos ->
        dtos.map { dto ->
            Item(
                id = dto.id,
                title = dto.title,
                price = dto.price / 100.0
            )
        }
    }
        .flowOn(Dispatchers.Default)
}

internal class FilterItemsUseCaseImpl : FilterItemUseCase {
    override fun invoke(items: List<Item>, minPrice: Double): List<Item> {
        return items.filter { it.price >= minPrice }
    }
}


class SortItemsUseCase: SortItemUseCase {
    override fun invoke(items: List<Item>, ascending: Boolean): List<Item> {
        return if (ascending) {
            items.sortedBy { it.price }
        } else {
            items.sortedByDescending { it.price }
        }
    }
}

internal class AddItemUseCaseImpl(
    private val repository: Repository
) : AddItemUseCase {
    override fun invoke(title: String, price: Int) {
        repository.addItem(title, price)
    }
}