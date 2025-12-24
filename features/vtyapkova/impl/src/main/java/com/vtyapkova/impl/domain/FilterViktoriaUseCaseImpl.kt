package com.vtyapkova.impl.domain

import com.vtyapkova.api.domain.models.ViktoriaDisplayModel
import com.vtyapkova.api.domain.usecases.FilterViktoriaUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FilterViktoriaUseCaseImpl(
    private val repository: ViktoriaRepository
) : FilterViktoriaUseCase {
    override fun execute(query: String): Flow<List<ViktoriaDisplayModel>> = repository.filterData(query)
        .map { filteredData ->
            filteredData.map { viktoriaData ->
                ViktoriaDisplayModel(
                    displayViktoria = viktoriaData.fullName,
                    shortViktoria = "${viktoriaData.firstName} ${viktoriaData.lastName.first()}.",
                    initials = viktoriaData.initials
                )
            }
        }
        .flowOn(Dispatchers.Default)
}

