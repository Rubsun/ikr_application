package com.example.ikr_application.telegin.domain

import com.example.ikr_application.telegin.data.FakeItemRepository

class AddItemUseCase(
    private val repository: FakeItemRepository,
) {
    suspend fun execute(title: String, description: String) {
        repository.addItem(title, description)
    }
}
