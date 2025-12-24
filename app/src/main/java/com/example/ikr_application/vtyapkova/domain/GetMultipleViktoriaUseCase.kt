package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.ViktoriaRepository
import com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetMultipleViktoriaUseCase {
    private val repository = ViktoriaRepository.INSTANCE

    fun execute(): Flow<List<ViktoriaDisplayModel>> = repository.getAllData()
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

