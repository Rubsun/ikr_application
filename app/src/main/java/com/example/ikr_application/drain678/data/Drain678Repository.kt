package com.example.ikr_application.drain678.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.drain678.data.model.Drain678Info
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Drain678Repository {
    private val _timeRecords = MutableStateFlow<List<Drain678Info>>(emptyList())
    val timeRecords: StateFlow<List<Drain678Info>> = _timeRecords.asStateFlow()

    fun drain678Info(): Drain678Info {
        return Drain678Info(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeRecord(info: Drain678Info) {
        _timeRecords.value = _timeRecords.value + info
    }

    fun getAllRecords(): StateFlow<List<Drain678Info>> {
        return timeRecords
    }

    companion object {
        @Discouraged("Drain678")
        val INSTANCE = Drain678Repository()
    }
}