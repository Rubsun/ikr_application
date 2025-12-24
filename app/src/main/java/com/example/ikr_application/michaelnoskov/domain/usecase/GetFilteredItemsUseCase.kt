package com.example.ikr_application.michaelnoskov.domain.usecase

import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository
import kotlinx.coroutines.flow.Flow

class GetFilteredItemsUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): Flow<List<FilteredItem>> = repository.getFilteredItems()
}