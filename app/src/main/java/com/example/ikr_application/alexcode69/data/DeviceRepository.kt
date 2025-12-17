package com.example.ikr_application.alexcode69.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.alexcode69.data.models.DeviceInfo
import com.example.ikr_application.alexcode69.data.models.TimeEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeviceRepository {
    private val _timeEntries = MutableStateFlow<List<TimeEntry>>(
        listOf(
            TimeEntry("1", System.currentTimeMillis() - 3600000, "Morning Start", true),
            TimeEntry("2", System.currentTimeMillis() - 1800000, "Break", false),
            TimeEntry("3", System.currentTimeMillis(), "Work Session", true),
        )
    )
    val timeEntries: StateFlow<List<TimeEntry>> = _timeEntries.asStateFlow()

    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeEntry(label: String) {
        val newEntry = TimeEntry(
            id = (_timeEntries.value.size + 1).toString(),
            timestamp = System.currentTimeMillis(),
            label = label,
            isActive = true
        )
        _timeEntries.value = _timeEntries.value + newEntry
    }

    fun removeTimeEntry(id: String) {
        _timeEntries.value = _timeEntries.value.filter { it.id != id }
    }

    fun updateTimeEntry(id: String, label: String, isActive: Boolean) {
        _timeEntries.value = _timeEntries.value.map { entry ->
            if (entry.id == id) entry.copy(label = label, isActive = isActive) else entry
        }
    }

    companion object {
        @Discouraged("Only for example")
        val INSTANCE = DeviceRepository()
    }
}