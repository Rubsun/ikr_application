package com.example.ikr_application.nastyazz.domain

import com.example.ikr_application.nastyazz.data.ItemRepository

class GetItemByIdUseCase(private val repo: ItemRepository) {
    suspend operator fun invoke(id: Int): Item? {
        return repo.fetchItemById(id)?.let { dto ->
            Item(dto.id, dto.title, dto.description)
        }
    }
}