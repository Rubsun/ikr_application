package com.vtyapkova.impl.domain

import com.vtyapkova.impl.data.ViktoriaRepository
import com.vtyapkova.impl.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetMultipleViktoriaUseCase(
    private val repository: ViktoriaRepository,
) {
    fun execute(): Flow<List<ViktoriaDisplayModel>> = repository.observeAllData()
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
