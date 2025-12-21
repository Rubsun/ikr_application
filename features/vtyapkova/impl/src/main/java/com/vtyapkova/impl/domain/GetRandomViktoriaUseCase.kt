package com.vtyapkova.impl.domain

import com.vtyapkova.impl.data.ViktoriaRepository
import com.vtyapkova.impl.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class GetRandomViktoriaUseCase(
    private val repository: ViktoriaRepository,
) {
    fun execute(): Flow<ViktoriaDisplayModel> = repository.getRandomViktoria()
        .map { viktoriaData ->
            ViktoriaDisplayModel(
                displayViktoria = viktoriaData.fullName,
                shortViktoria = "${viktoriaData.firstName} ${viktoriaData.lastName.first()}.",
                initials = viktoriaData.initials
            )
        }
        .flowOn(Dispatchers.Default)
}
