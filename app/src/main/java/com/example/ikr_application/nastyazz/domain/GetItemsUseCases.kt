package com.example.ikr_application.nastyazz.domain

import com.example.ikr_application.nastyazz.data.ItemRepository


class GetItemsUseCase(private val repo: ItemRepository) {
    suspend operator fun invoke(): List<Item> {
        return repo.fetchItems().map { dto ->
            Item(dto.id, dto.title, dto.description)
        }
    }
}