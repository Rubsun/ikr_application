package com.example.ikr_application.dyatlova.domain

class FilterDestinationsUseCase {
    operator fun invoke(destinations: List<Destination>, rawQuery: String): List<Destination> {
        val query = rawQuery.trim().lowercase()
        if (query.isEmpty()) return destinations

        return destinations.filter { destination ->
            destination.title.contains(query, ignoreCase = true) ||
                destination.country.contains(query, ignoreCase = true) ||
                destination.tags.any { it.contains(query, ignoreCase = true) }
        }
    }
}

