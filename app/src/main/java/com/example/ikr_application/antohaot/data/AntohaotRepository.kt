package com.example.ikr_application.antohaot.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.antohaot.data.models.AntohaotInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AntohaotRepository {
    private val _timeRecords = MutableStateFlow<List<AntohaotInfo>>(emptyList())
    val timeRecords: StateFlow<List<AntohaotInfo>> = _timeRecords.asStateFlow()

    fun antohaotInfo(): AntohaotInfo {
        return AntohaotInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeRecord(info: AntohaotInfo) {
        _timeRecords.value = _timeRecords.value + info
    }

    fun getAllRecords(): StateFlow<List<AntohaotInfo>> {
        return timeRecords
    }

    companion object {
        @Discouraged("Antohaot")
        val INSTANCE = AntohaotRepository()
    }
}