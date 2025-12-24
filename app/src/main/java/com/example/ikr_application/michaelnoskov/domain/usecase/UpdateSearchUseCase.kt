package com.example.ikr_application.michaelnoskov.domain.usecase

import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository

class UpdateSearchUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(query: String) = repository.updateSearchQuery(query)
}