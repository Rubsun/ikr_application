package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.NameRepository
import com.example.ikr_application.vtyapkova.domain.models.NameDisplayModel

class GetRandomNameUseCase {
    private val repository = NameRepository.INSTANCE

    fun execute(): NameDisplayModel {
        val nameData = repository.generateRandomName()
        return NameDisplayModel(
            displayName = nameData.fullName,
            shortName = "${nameData.firstName} ${nameData.lastName.first()}.",
            initials = nameData.initials
        )
    }
}

