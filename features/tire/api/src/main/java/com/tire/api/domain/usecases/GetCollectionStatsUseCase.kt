package com.tire.api.domain.usecases

import com.tire.api.domain.models.CollectionStats
import kotlinx.coroutines.flow.Flow

interface GetCollectionStatsUseCase {
    operator fun invoke(): Flow<CollectionStats>
}
