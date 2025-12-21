package com.dimmension.impl.domain

import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.ObserveNamesUseCase
import com.dimmension.impl.data.NameRepository
import com.dimmension.impl.data.models.NameRecordDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ObserveNamesUseCaseImpl(
    private val repository: NameRepository,
) : ObserveNamesUseCase {
    override fun invoke(): Flow<List<NameDisplayModel>> {
        return repository.namesFlow
            .map { names -> names.map(NameRecordDto::toDomain) }
    }
}


