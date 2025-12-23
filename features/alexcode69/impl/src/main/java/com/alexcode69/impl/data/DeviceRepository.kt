package com.alexcode69.impl.data

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import com.alexcode69.api.domain.models.DeviceInfo
import com.alexcode69.api.domain.models.TimeEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
private data class TimeEntrySerializable(
    val id: String,
    val timestamp: Long,
    val label: String,
    val isActive: Boolean = false,
) {
    fun toTimeEntry(): TimeEntry {
        return TimeEntry(
            id = id,
            timestamp = timestamp,
            label = label,
            isActive = isActive
        )
    }
}

private fun TimeEntry.toSerializable(): TimeEntrySerializable {
    return TimeEntrySerializable(
        id = id,
        timestamp = timestamp,
        label = label,
        isActive = isActive
    )
}

internal class DeviceRepository(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "alexcode69_prefs",
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }
    private val entriesKey = "time_entries"

    private val _timeEntries = MutableStateFlow<List<TimeEntry>>(loadEntriesSync())
    val timeEntries: StateFlow<List<TimeEntry>> = _timeEntries.asStateFlow()

    init {
        _timeEntries.value = loadEntriesSync()
    }

    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    suspend fun addTimeEntry(label: String) = withContext(Dispatchers.IO) {
        val newEntry = TimeEntry(
            id = (_timeEntries.value.size + 1).toString(),
            timestamp = System.currentTimeMillis(),
            label = label,
            isActive = true
        )
        val updatedEntries = _timeEntries.value + newEntry
        _timeEntries.value = updatedEntries
        saveEntries(updatedEntries)
    }

    fun removeTimeEntry(id: String) {
        val updatedEntries = _timeEntries.value.filter { it.id != id }
        _timeEntries.value = updatedEntries
        saveEntries(updatedEntries)
    }

    fun updateTimeEntry(id: String, label: String, isActive: Boolean) {
        val updatedEntries = _timeEntries.value.map { entry ->
            if (entry.id == id) entry.copy(label = label, isActive = isActive) else entry
        }
        _timeEntries.value = updatedEntries
        saveEntries(updatedEntries)
    }

    private fun loadEntriesSync(): List<TimeEntry> {
        val entriesJson = sharedPreferences.getString(entriesKey, null) ?: return getDefaultEntries()
        return try {
            val serializable = json.decodeFromString<List<TimeEntrySerializable>>(entriesJson)
            serializable.map { it.toTimeEntry() }
        } catch (e: Exception) {
            getDefaultEntries()
        }
    }

    private fun getDefaultEntries(): List<TimeEntry> {
        return listOf(
            TimeEntry("1", System.currentTimeMillis() - 3600000, "Morning Start", true),
            TimeEntry("2", System.currentTimeMillis() - 1800000, "Break", false),
            TimeEntry("3", System.currentTimeMillis(), "Work Session", true),
        )
    }

    private fun saveEntries(entries: List<TimeEntry>) {
        try {
            val serializable = entries.map { it.toSerializable() }
            val entriesJson = json.encodeToString(serializable)
            sharedPreferences.edit().putString(entriesKey, entriesJson).commit()
        } catch (e: Exception) {
            // Ignore
        }
    }
}

