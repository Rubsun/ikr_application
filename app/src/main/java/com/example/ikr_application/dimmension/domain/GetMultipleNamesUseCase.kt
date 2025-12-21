package com.example.ikr_application.dimmension.domain

import com.example.ikr_application.dimmension.data.NameRepository
import com.example.ikr_application.dimmension.domain.models.NameDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetMultipleNamesUseCase {
    private val repository = NameRepository.INSTANCE

    fun execute(): Flow<List<NameDisplayModel>> {
        return repository.getAllNames().map { namesData ->
            namesData.map { nameData ->
                NameDisplayModel(
                    displayName = nameData.fullName,
                    shortName = "${nameData.firstName} ${nameData.lastName.first()}.",
                    initials = nameData.initials
                )
            }
        }
    }

    suspend fun generateNewNames(count: Int = 5): List<NameDisplayModel> = withContext(Dispatchers.Default) {
        val namesData = repository.generateMultipleNames(count)
        namesData.map { nameData ->
            NameDisplayModel(
                displayName = nameData.fullName,
                shortName = "${nameData.firstName} ${nameData.lastName.first()}.",
                initials = nameData.initials
            )
        }
    }
}

