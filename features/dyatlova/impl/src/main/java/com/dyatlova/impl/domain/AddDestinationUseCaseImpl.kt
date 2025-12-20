package com.dyatlova.impl.domain

import com.dyatlova.api.domain.models.Destination
import com.dyatlova.api.domain.usecases.AddDestinationUseCase
import com.dyatlova.impl.data.DestinationRepository
import com.dyatlova.impl.data.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddDestinationUseCaseImpl(
    private val repository: DestinationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AddDestinationUseCase {
    override suspend fun invoke(destination: Destination) {
        withContext(dispatcher) {
            repository.addDestination(destination.toData())
        }
    }
}



