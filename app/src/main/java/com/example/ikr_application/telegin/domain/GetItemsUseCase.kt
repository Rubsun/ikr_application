package com.example.ikr_application.telegin.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.ikr_application.telegin.data.FakeItemRepository

class GetItemsUseCase(
    private val repository: FakeItemRepository,
) {
    fun execute(): Flow<List<Item>> =
        repository.observeItems().map { list ->
            list.map { dto ->
                Item(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                )
            }
        }
}
