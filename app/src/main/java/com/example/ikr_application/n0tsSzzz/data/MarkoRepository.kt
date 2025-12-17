package com.example.ikr_application.n0tsSzzz.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MarkoRepository {
    private val _timeRecords = MutableStateFlow<List<MarkoInfo>>(emptyList())
    val timeRecords: StateFlow<List<MarkoInfo>> = _timeRecords.asStateFlow()

    fun deviceInfo(): MarkoInfo {
        return MarkoInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeRecord(info: MarkoInfo) {
        _timeRecords.value += info
    }

    fun getAllRecords(): StateFlow<List<MarkoInfo>> {
        return timeRecords
    }

    companion object {
        @Discouraged("Only for marko")
        val INSTANCE = MarkoRepository()
    }
}
