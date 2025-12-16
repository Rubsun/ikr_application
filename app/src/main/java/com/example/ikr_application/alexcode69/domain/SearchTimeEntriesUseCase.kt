package com.example.ikr_application.alexcode69.domain

import com.example.ikr_application.alexcode69.data.DeviceRepository
import com.example.ikr_application.alexcode69.data.models.TimeEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchTimeEntriesUseCase(private val repository: DeviceRepository = DeviceRepository.INSTANCE) {
    fun search(query: String): Flow<List<TimeEntry>> {
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
