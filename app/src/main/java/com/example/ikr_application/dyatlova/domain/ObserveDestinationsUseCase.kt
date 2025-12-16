package com.example.ikr_application.dyatlova.domain

import com.example.ikr_application.dyatlova.data.DestinationData
import com.example.ikr_application.dyatlova.data.DestinationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveDestinationsUseCase(
    private val repository: DestinationRepository,
) {
    operator fun invoke(): Flow<List<Destination>> = repository.observeDestinations()
        .map { data -> data.map(DestinationData::toDomain) }
}

private fun DestinationData.toDomain(): Destination = Destination(
    id = id,
    title = title,
    country = country,
    imageUrl = imageUrl,
    tags = tags,
)

