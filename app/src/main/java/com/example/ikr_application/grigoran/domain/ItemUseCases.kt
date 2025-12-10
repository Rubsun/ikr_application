package com.example.ikr_application.grigoran.domain

import com.example.ikr_application.grigoran.data.Repository

class ItemUseCases (
    private val repository: Repository
) {
    operator fun invoke(): List<ItemDomain> {
        val dtos = repository.fetchItem()
        return  dtos.map { dto -> ItemDomain(id = dto.id, title = dto.title, price = dto.price / 100.0) }
    }
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