package com.tire.api.domain.usecases

import com.tire.api.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetMyCollectionUseCase {
    operator fun invoke(): Flow<List<Pokemon>>
}
