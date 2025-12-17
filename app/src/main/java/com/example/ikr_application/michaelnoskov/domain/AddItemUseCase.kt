package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository

class AddItemUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(text: String) {
        repository.addItem(text)
    }
}