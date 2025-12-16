package com.example.ikr_application.dyatlova.domain

import com.example.ikr_application.dyatlova.data.DestinationData
import com.example.ikr_application.dyatlova.data.DestinationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddDestinationUseCase(
    private val repository: DestinationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend operator fun invoke(destination: Destination) {
        withContext(dispatcher) {
            repository.addDestination(destination.toData())
        }
    }
}

private fun Destination.toData(): DestinationData = DestinationData(
    id = id,
    title = title,
    country = country,
    imageUrl = imageUrl,
    tags = tags,
)

