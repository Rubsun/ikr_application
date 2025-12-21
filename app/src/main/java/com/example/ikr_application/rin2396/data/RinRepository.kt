package com.example.ikr_application.rin2396.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.rin2396.data.models.RinInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class RinRepository {
    private val _timeEntries = MutableStateFlow<List<RinInfo>>(emptyList())
    val timeEntries: StateFlow<List<RinInfo>> = _timeEntries.asStateFlow()

    suspend fun rinInfo(): RinInfo = withContext(Dispatchers.IO) {
        delay(10) // Simulate some work
        RinInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    suspend fun addTimeEntry(info: RinInfo) = withContext(Dispatchers.IO) {
        delay(10) // Simulate some work
        _timeEntries.value = _timeEntries.value + info
    }

    companion object {
        @Discouraged("Rin")
        val INSTANCE = RinRepository()
    }
}