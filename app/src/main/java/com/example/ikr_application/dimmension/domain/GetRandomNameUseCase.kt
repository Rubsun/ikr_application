package com.example.ikr_application.dimmension.domain

import com.example.ikr_application.dimmension.data.NameRepository
import com.example.ikr_application.dimmension.domain.models.NameDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRandomNameUseCase {
    private val repository = NameRepository.INSTANCE

    suspend fun execute(): NameDisplayModel = withContext(Dispatchers.Default) {
        val nameData = repository.generateRandomName()
        NameDisplayModel(
            displayName = nameData.fullName,
            shortName = "${nameData.firstName} ${nameData.lastName.first()}.",
            initials = nameData.initials
        )
    }
}

