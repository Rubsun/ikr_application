package com.drain678.impl.domain

import com.drain678.api.domain.models.Drain678Info
import com.drain678.api.domain.usecases.FilterTimeRecordsUseCase
import com.drain678.impl.data.Drain678Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat

internal class FilterTimeRecordsUseCaseImpl(
    private val repository: Drain678Repository
) : FilterTimeRecordsUseCase {
    private val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    override fun invoke(query: String): Flow<List<Drain678Info>> = repository.getAllRecords()
        .map { records ->
            if (query.isBlank()) {
                records
            } else {
                val lowerQuery = query.lowercase()
                records.filter { record ->
                    val formattedDate = formatCurrentTime(record.currentTime).lowercase()
                    val formattedElapsed = formatElapsedTime(record.elapsedTime).lowercase()
                    
                    formattedDate.contains(lowerQuery) ||
                    formattedElapsed.contains(lowerQuery) ||
                    record.currentTime.toString().contains(query, ignoreCase = true) ||
                    record.elapsedTime.toString().contains(query, ignoreCase = true)
                }
            }
        }
        .flowOn(Dispatchers.Default)

    private fun formatCurrentTime(currentTime: Long): String {
        val dateTime = DateTime(currentTime)
        return dateTimeFormatter.print(dateTime)
    }

    private fun formatElapsedTime(elapsedTime: Long): String {
        val duration = Duration(elapsedTime)
        val hours = duration.standardHours
        val minutes = duration.standardMinutes % 60
        val seconds = duration.standardSeconds % 60
        val milliseconds = duration.millis % 1000 / 100
        return String.format("%02d:%02d:%02d.%01d", hours, minutes, seconds, milliseconds)
    }
}

