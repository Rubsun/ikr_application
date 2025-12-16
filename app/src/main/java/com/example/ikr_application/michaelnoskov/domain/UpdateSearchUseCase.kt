package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository

class UpdateSearchUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(query: String) {
        repository.updateSearchQuery(query)
    }
}