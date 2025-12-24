package com.example.ikr_application.michaelnoskov.domain.usecase

import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository

class AddItemUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(text: String) = repository.addItem(text)
}