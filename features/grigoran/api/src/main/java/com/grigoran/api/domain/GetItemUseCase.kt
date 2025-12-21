package com.grigoran.api.domain

import com.grigoran.api.models.Item
import kotlinx.coroutines.flow.Flow

interface GetItemUseCase {
    operator fun invoke(): Flow<List<Item>>
}