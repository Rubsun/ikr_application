package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.ViktoriaRepository
import com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel

class GetMultipleViktoriaUseCase {
    private val repository = ViktoriaRepository.INSTANCE

    fun execute(count: Int = 5): List<ViktoriaDisplayModel> {
        val ViktoriaData = repository.generateMultipleViktoria(count)
        return ViktoriaData.map { ViktoriaData ->
            ViktoriaDisplayModel(
                displayViktoria = ViktoriaData.fullName,
                shortViktoria = "${ViktoriaData.firstName} ${ViktoriaData.lastName.first()}.",
                initials = ViktoriaData.initials
            )
        }
    }
}

