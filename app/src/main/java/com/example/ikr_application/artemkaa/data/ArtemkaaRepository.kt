package com.example.ikr_application.artemkaa.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.artemkaa.data.models.ArtemkaaInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArtemkaaRepository {
    private val _timeRecords = MutableStateFlow<List<ArtemkaaInfo>>(emptyList())
    val timeRecords: StateFlow<List<ArtemkaaInfo>> = _timeRecords.asStateFlow()

    fun artemkaaInfo(): ArtemkaaInfo {
        return ArtemkaaInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeRecord(info: ArtemkaaInfo) {
        _timeRecords.value = _timeRecords.value + info
    }

    fun getAllRecords(): StateFlow<List<ArtemkaaInfo>> {
        return timeRecords
    }

    companion object {
        @Discouraged("Artemkaa")
        val INSTANCE = ArtemkaaRepository()
    }
}