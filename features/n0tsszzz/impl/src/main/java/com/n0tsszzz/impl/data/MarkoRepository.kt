package com.n0tsszzz.impl.data

import android.content.SharedPreferences
import android.os.SystemClock
import android.util.Log
import com.n0tsszzz.api.domain.models.MarkoInfo
import com.n0tsszzz.network.api.TimeApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "MarkoRepository"

internal class MarkoRepository(
    private val api: TimeApiClient,
    private val sharedPreferences: SharedPreferences,
    private val json: Json
) {
    
    private val _timeRecords = MutableStateFlow<List<MarkoInfo>>(emptyList())
    val timeRecords: StateFlow<List<MarkoInfo>> = _timeRecords.asStateFlow()

    init {
        Log.d(TAG, "MarkoRepository initialized, api: $api")
        _timeRecords.value = loadRecordsSync()
    }

    suspend fun getCurrentTimeFromApi(): MarkoInfo {
        Log.d(TAG, "getCurrentTimeFromApi() called")
        val localTimeBeforeApi = System.currentTimeMillis()
        Log.d(TAG, "Local time before API call: $localTimeBeforeApi (${java.util.Date(localTimeBeforeApi)})")
        
        return try {
            Log.d(TAG, "Calling api.getCurrentTime()")
            val timeDto = api.getCurrentTime()
            Log.d(TAG, "Got timeDto from API: $timeDto")
            val apiTimeMillis = timeDto.unixtime * 1000 // Convert seconds to milliseconds
            Log.d(TAG, "API time in milliseconds: $apiTimeMillis (${java.util.Date(apiTimeMillis)})")
            Log.d(TAG, "Difference between API and local time: ${apiTimeMillis - localTimeBeforeApi} ms")
            
            val result = MarkoInfo(
                currentTime = apiTimeMillis, // Используем время из API, НЕ локальное!
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            Log.d(TAG, "Returning MarkoInfo with API time: currentTime=${result.currentTime}")
            result
        } catch (e: java.net.UnknownHostException) {
            Log.e(TAG, "UnknownHostException in getCurrentTimeFromApi - FALLBACK to local time", e)
            // Network error - fallback to local time
            val fallback = deviceInfo()
            Log.w(TAG, "Using FALLBACK local time: ${fallback.currentTime} (${java.util.Date(fallback.currentTime)})")
            fallback
        } catch (e: java.io.IOException) {
            Log.e(TAG, "IOException in getCurrentTimeFromApi - FALLBACK to local time", e)
            // Network error - fallback to local time
            val fallback = deviceInfo()
            Log.w(TAG, "Using FALLBACK local time: ${fallback.currentTime} (${java.util.Date(fallback.currentTime)})")
            fallback
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getCurrentTimeFromApi - FALLBACK to local time", e)
            // Any other error - fallback to local time
            val fallback = deviceInfo()
            Log.w(TAG, "Using FALLBACK local time: ${fallback.currentTime} (${java.util.Date(fallback.currentTime)})")
            fallback
        }
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

