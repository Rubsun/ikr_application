package com.dyatlova.api.domain.usecases

import com.dyatlova.api.domain.models.Destination

interface AddDestinationUseCase {
    suspend operator fun invoke(destination: Destination)
}



