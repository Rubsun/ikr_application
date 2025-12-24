package com.dimmension.impl.domain

import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.GenerateNamesUseCase
import com.dimmension.impl.data.NameRepository
import com.dimmension.impl.data.models.NameRecordDto

internal class GenerateNamesUseCaseImpl(
    private val repository: NameRepository,
) : GenerateNamesUseCase {
    override suspend fun invoke(count: Int): List<NameDisplayModel> {
        return repository.generateBatch(count)
            .map(NameRecordDto::toDomain)
    }
}


