package com.example.ikr_application.n0tsSzzz.domain

import android.annotation.SuppressLint
import com.example.ikr_application.n0tsSzzz.data.MarkoRepository
import com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("DiscouragedApi")
internal class GetTimeRecordsUseCase(
    @param:SuppressLint("DiscouragedApi")
    private val repository: MarkoRepository = MarkoRepository.INSTANCE
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun invoke(query: String = ""): Flow<List<MarkoInfo>> = repository.getAllRecords()
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
