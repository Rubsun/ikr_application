package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.repository.ColorSquareRepository

internal class UpdateSearchUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(query: String) = repository.updateSearchQuery(query)
}

