package com.dyatlova.api.domain.usecases

import com.dyatlova.api.domain.models.Destination
import kotlinx.coroutines.flow.Flow

interface ObserveDestinationsUseCase {
    operator fun invoke(): Flow<List<Destination>>
}



