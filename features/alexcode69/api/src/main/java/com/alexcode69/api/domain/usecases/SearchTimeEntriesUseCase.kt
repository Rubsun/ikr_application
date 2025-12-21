package com.alexcode69.api.domain.usecases

import com.alexcode69.api.domain.models.TimeEntry
import kotlinx.coroutines.flow.Flow

interface SearchTimeEntriesUseCase {
    fun search(query: String): Flow<List<TimeEntry>>
}

