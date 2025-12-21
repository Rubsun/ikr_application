package com.antohaot.api.domain.usecases

import com.antohaot.api.domain.models.AntohaotInfo
import kotlinx.coroutines.flow.Flow

interface FilterTimeRecordsUseCase {
    fun invoke(query: String = ""): Flow<List<AntohaotInfo>>
}

