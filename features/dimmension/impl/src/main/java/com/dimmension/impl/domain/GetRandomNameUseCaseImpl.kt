package com.dimmension.impl.domain

import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.GetRandomNameUseCase
import com.dimmension.impl.data.NameRepository
import com.dimmension.impl.data.models.NameRecordDto

internal class GetRandomNameUseCaseImpl(
    private val repository: NameRepository,
) : GetRandomNameUseCase {
    override suspend fun invoke(): NameDisplayModel {
        return repository.generateRandomName()
            .let(NameRecordDto::toDomain)
    }
}


