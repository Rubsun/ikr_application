package com.tire.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import com.tire.api.domain.models.CollectionStats

interface GetCollectionStatsUseCase {
    operator fun invoke(): Flow<CollectionStats>
}
