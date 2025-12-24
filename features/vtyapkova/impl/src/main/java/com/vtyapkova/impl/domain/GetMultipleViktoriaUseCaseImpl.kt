package com.vtyapkova.impl.domain

import com.vtyapkova.api.domain.models.ViktoriaDisplayModel
import com.vtyapkova.api.domain.usecases.GetMultipleViktoriaUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetMultipleViktoriaUseCaseImpl(
    private val repository: ViktoriaRepository
) : GetMultipleViktoriaUseCase {
    override fun execute(): Flow<List<ViktoriaDisplayModel>> = repository.getAllData()
        .map { viktoriaDataList ->
            viktoriaDataList.map { viktoriaData ->
                ViktoriaDisplayModel(
                    displayViktoria = viktoriaData.fullName,
                    shortViktoria = "${viktoriaData.firstName} ${viktoriaData.lastName.first()}.",
                    initials = viktoriaData.initials
                )
            }
        }
}

