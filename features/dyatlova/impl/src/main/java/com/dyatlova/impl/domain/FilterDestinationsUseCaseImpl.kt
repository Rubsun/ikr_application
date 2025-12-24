package com.dyatlova.impl.domain

import com.dyatlova.api.domain.models.Destination
import com.dyatlova.api.domain.usecases.FilterDestinationsUseCase

internal class FilterDestinationsUseCaseImpl : FilterDestinationsUseCase {
    override fun invoke(destinations: List<Destination>, query: String): List<Destination> {
        val trimmedQuery = query.trim().lowercase()
        if (trimmedQuery.isEmpty()) return destinations

        return destinations.filter { destination ->
            destination.title.contains(trimmedQuery, ignoreCase = true) ||
                destination.country.contains(trimmedQuery, ignoreCase = true) ||
                destination.tags.any { it.contains(trimmedQuery, ignoreCase = true) }
        }
    }
}



