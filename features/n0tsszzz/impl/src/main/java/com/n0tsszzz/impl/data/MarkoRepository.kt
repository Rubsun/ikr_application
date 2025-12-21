package com.n0tsszzz.impl.data

import android.content.SharedPreferences
import android.os.SystemClock
import com.n0tsszzz.api.domain.models.MarkoInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class MarkoRepository(
    private val sharedPreferences: SharedPreferences,
    private val json: Json
) {
    
    private val _timeRecords = MutableStateFlow<List<MarkoInfo>>(emptyList())
    val timeRecords: StateFlow<List<MarkoInfo>> = _timeRecords.asStateFlow()

    init {
        _timeRecords.value = loadRecordsSync()
    }

    fun deviceInfo(): MarkoInfo {
        return MarkoInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeRecord(info: MarkoInfo) {
        val updatedRecords = _timeRecords.value + info
        _timeRecords.value = updatedRecords
        saveRecords(updatedRecords)
    }

    fun getAllRecords(): StateFlow<List<MarkoInfo>> {
        return timeRecords
    }

    fun clearAllRecords() {
        _timeRecords.value = emptyList()
        saveRecords(emptyList())
    }

    private fun loadRecordsSync(): List<MarkoInfo> {
        val recordsJson = sharedPreferences.getString(RECORDS_KEY, null) ?: return emptyList()
        return try {
            json.decodeFromString<List<MarkoInfo>>(recordsJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveRecords(records: List<MarkoInfo>) {
        try {
            val recordsJson = json.encodeToString(records)
            sharedPreferences.edit().putString(RECORDS_KEY, recordsJson).commit()
        } catch (e: Exception) {
        }
    }

    companion object {
        private const val RECORDS_KEY = "time_records"
    }
}

