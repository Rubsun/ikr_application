package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository
import com.example.ikr_application.michaelnoskov.data.models.FilteredItem
import kotlinx.coroutines.flow.Flow

class GetFilteredItemsUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): Flow<List<FilteredItem>> {
        return repository.filteredItems
    }
}