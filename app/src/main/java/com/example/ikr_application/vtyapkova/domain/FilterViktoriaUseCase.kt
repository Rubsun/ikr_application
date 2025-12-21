package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.ViktoriaRepository
import com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FilterViktoriaUseCase {
    private val repository = ViktoriaRepository.INSTANCE

    fun execute(query: String): Flow<List<ViktoriaDisplayModel>> = repository.filterData(query)
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

