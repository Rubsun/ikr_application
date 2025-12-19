package com.example.ikr_application.drain678.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.drain678.data.Drain678Repository
import com.example.ikr_application.drain678.data.model.Drain678Info
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat

internal class FilterTimeRecordsUseCase(
    @param:Discouraged("Drain678")
    private val repository: Drain678Repository = Drain678Repository.INSTANCE
) {
    private val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    fun invoke(query: String = ""): Flow<List<Drain678Info>> = repository.getAllRecords()
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

