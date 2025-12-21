package com.dimmension.api.domain.usecases

import com.dimmension.api.domain.models.NameDisplayModel
import kotlinx.coroutines.flow.Flow

interface ObserveNamesUseCase {
    operator fun invoke(): Flow<List<NameDisplayModel>>
}


