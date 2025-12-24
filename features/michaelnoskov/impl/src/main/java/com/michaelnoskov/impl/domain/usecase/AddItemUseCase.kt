package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.repository.ColorSquareRepository

internal class AddItemUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(text: String) = repository.addItem(text)
}

