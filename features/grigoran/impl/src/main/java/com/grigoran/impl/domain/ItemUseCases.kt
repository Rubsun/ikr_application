package com.grigoran.impl.domain

import com.grigoran.impl.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ItemUseCases (
    private val repository: Repository
) {
    operator fun invoke() = repository.observeItems().map { dtos ->
        dtos.map { dto ->
            ItemDomain(
                id = dto.id,
                title = dto.title,
                price = dto.price / 100.0
            )
        }
    }
        .flowOn(Dispatchers.Default)
}

class FilterItemsUseCase {
    operator fun invoke(items: List<ItemDomain>, minPrice: Double): List<ItemDomain> {
        return items.filter { it.price >= minPrice }
    }
}


class SortItemsUseCase {
    operator fun invoke(items: List<ItemDomain>, ascending: Boolean = true): List<ItemDomain> {
        return if (ascending) {
            items.sortedBy { it.price }
        } else {
            items.sortedByDescending { it.price }
        }
    }
}

class AddItemUseCase(
    private val repository: Repository
) {
    operator fun invoke(title: String, price: Int) {
        repository.addItem(title, price)
    }
}