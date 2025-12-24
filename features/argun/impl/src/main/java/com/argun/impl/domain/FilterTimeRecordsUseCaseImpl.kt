package com.argun.impl.domain

import com.argun.api.domain.models.ArgunInfo
import com.argun.api.domain.usecases.FilterTimeRecordsUseCase
import com.argun.impl.data.ArgunRepository
import com.argun.network.api.TimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FilterTimeRecordsUseCaseImpl(
    private val repository: ArgunRepository,
    private val timeFormatter: TimeFormatter
) : FilterTimeRecordsUseCase {

    override fun invoke(query: String): Flow<List<ArgunInfo>> = repository.getAllRecords()
        .map { records ->
            if (query.isBlank()) {
                records
            } else {
                val lowerQuery = query.lowercase()
                records.filter { record ->
                    val formattedDate = timeFormatter.formatDateTime(record.currentTime).lowercase()
                    val formattedElapsed = timeFormatter.formatElapsedTime(record.elapsedTime).lowercase()
                    
                    formattedDate.contains(lowerQuery) ||
                    formattedElapsed.contains(lowerQuery) ||
                    record.currentTime.toString().contains(query, ignoreCase = true) ||
                    record.elapsedTime.toString().contains(query, ignoreCase = true)
                }
            }
        }
        .flowOn(Dispatchers.Default)
}

