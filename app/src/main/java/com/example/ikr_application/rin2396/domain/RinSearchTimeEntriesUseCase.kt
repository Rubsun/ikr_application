package com.example.ikr_application.rin2396.domain

import com.example.ikr_application.rin2396.data.RinRepository
import com.example.ikr_application.rin2396.data.models.RinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RinSearchTimeEntriesUseCase {
    private val repository = RinRepository.INSTANCE

    fun search(query: String): Flow<List<RinInfo>> {
        return repository.timeEntries.map { entries ->
            if (query.isBlank()) {
                entries
            } else {
                entries.filter {
                    it.currentTime.toString().contains(query, ignoreCase = true) ||
                    it.elapsedTime.toString().contains(query, ignoreCase = true)
                }
            }
        }
    }
}

