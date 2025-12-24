package com.vtyapkova.impl.domain

import com.vtyapkova.api.domain.models.ViktoriaDisplayModel
import com.vtyapkova.api.domain.usecases.GetRandomViktoriaUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class GetRandomViktoriaUseCaseImpl(
    private val repository: ViktoriaRepository
) : GetRandomViktoriaUseCase {
    override fun execute(): Flow<ViktoriaDisplayModel> = repository.getRandomViktoria()
        .map { viktoriaData ->
            ViktoriaDisplayModel(
                displayViktoria = viktoriaData.fullName,
                shortViktoria = "${viktoriaData.firstName} ${viktoriaData.lastName.first()}.",
                initials = viktoriaData.initials
            )
        }
        .flowOn(Dispatchers.Default)
}

