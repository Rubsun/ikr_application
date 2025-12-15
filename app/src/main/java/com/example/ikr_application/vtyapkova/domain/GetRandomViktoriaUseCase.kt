package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.ViktoriaRepository
import com.example.ikr_application.vtyapkova.domain.models.ViktoriaDisplayModel

class GetRandomViktoriaUseCase {
    private val repository = ViktoriaRepository.INSTANCE

    fun execute(): ViktoriaDisplayModel {
        val ViktoriaData = repository.generateRandomViktoria()
        return ViktoriaDisplayModel(
            displayViktoria = ViktoriaData.fullName,
            shortViktoria = "${ViktoriaData.firstName} ${ViktoriaData.lastName.first()}.",
            initials = ViktoriaData.initials
        )
    }
}

