package com.alexcode69.impl.domain

import com.alexcode69.api.domain.models.TimeEntry
import com.alexcode69.api.domain.usecases.SearchTimeEntriesUseCase
import com.alexcode69.impl.data.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchTimeEntriesUseCaseImpl(
    private val repository: DeviceRepository
) : SearchTimeEntriesUseCase {
    override fun search(query: String): Flow<List<TimeEntry>> {
        return repository.timeEntries.map { entries ->
            if (query.isEmpty()) {
                entries
            } else {
                entries.filter { entry ->
                    entry.label.contains(query, ignoreCase = true)
                }
            }
        }
    }
}

