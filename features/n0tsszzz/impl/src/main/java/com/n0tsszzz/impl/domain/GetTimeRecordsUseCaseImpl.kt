package com.n0tsszzz.impl.domain

import com.n0tsszzz.api.domain.models.MarkoInfo
import com.n0tsszzz.api.domain.usecases.GetTimeRecordsUseCase
import com.n0tsszzz.impl.data.MarkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class GetTimeRecordsUseCaseImpl(
    private val repository: MarkoRepository
) : GetTimeRecordsUseCase {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun invoke(query: String): Flow<List<MarkoInfo>> = repository.getAllRecords()
        .map { records ->
            if (query.isBlank()) {
                records
            } else {
                val lowerQuery = query.lowercase()
                records.filter { record ->
                    val formattedDate = dateFormat.format(Date(record.currentTime)).lowercase()
                    formattedDate.contains(lowerQuery) ||
                    record.currentTime.toString().contains(query, ignoreCase = true) ||
                    record.elapsedTime.toString().contains(query, ignoreCase = true)
                }
            }
        }
        .flowOn(Dispatchers.Default)
}

