package com.dyatlova.impl.domain

import com.dyatlova.api.domain.models.Destination
import com.dyatlova.api.domain.usecases.ObserveDestinationsUseCase
import com.dyatlova.impl.data.DestinationRepository
import com.dyatlova.impl.data.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ObserveDestinationsUseCaseImpl(
    private val repository: DestinationRepository,
) : ObserveDestinationsUseCase {
    override fun invoke(): Flow<List<Destination>> = repository.observeDestinations()
        .map { data -> data.map { it.toDomain() } }
}



