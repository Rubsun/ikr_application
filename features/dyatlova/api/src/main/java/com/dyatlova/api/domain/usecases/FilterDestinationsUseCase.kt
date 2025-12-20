package com.dyatlova.api.domain.usecases

import com.dyatlova.api.domain.models.Destination

interface FilterDestinationsUseCase {
    operator fun invoke(destinations: List<Destination>, query: String): List<Destination>
}

