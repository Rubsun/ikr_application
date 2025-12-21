package com.vtyapkova.impl.domain

import com.vtyapkova.impl.data.ViktoriaRepository
import com.vtyapkova.impl.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class FilterViktoriaUseCase(
    private val repository: ViktoriaRepository,
) {
    fun execute(query: String): Flow<List<ViktoriaDisplayModel>> = flow {
        delay(150)
        val all = repository.observeAllData().first()
        val filtered = if (query.isBlank()) {
            all
        } else {
            val lowerQuery = query.lowercase()
            all.filter {
                it.firstName.lowercase().contains(lowerQuery) ||
                    it.lastName.lowercase().contains(lowerQuery) ||
                    it.fullName.lowercase().contains(lowerQuery)
            }
        }

        emit(
            filtered.map { viktoriaData ->
                ViktoriaDisplayModel(
                    displayViktoria = viktoriaData.fullName,
                    shortViktoria = "${viktoriaData.firstName} ${viktoriaData.lastName.first()}.",
                    initials = viktoriaData.initials
                )
            }
        )
    }.flowOn(Dispatchers.Default)
}
