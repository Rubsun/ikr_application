package com.example.ikr_application.dimmension.domain

import com.example.ikr_application.dimmension.data.NameRepository
import com.example.ikr_application.dimmension.domain.models.NameDisplayModel

class GetMultipleNamesUseCase {
    private val repository = NameRepository.INSTANCE

    fun execute(count: Int = 5): List<NameDisplayModel> {
        val namesData = repository.generateMultipleNames(count)
        return namesData.map { nameData ->
            NameDisplayModel(
                displayName = nameData.fullName,
                shortName = "${nameData.firstName} ${nameData.lastName.first()}.",
                initials = nameData.initials
            )
        }
    }
}

