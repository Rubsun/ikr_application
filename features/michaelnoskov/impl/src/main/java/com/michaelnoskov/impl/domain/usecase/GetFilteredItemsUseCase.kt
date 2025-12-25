package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.repository.ColorSquareRepository
import kotlinx.coroutines.flow.Flow

internal class GetFilteredItemsUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): Flow<List<FilteredItem>> = repository.getFilteredItems()
}

