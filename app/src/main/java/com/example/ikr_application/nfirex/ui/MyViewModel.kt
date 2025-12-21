package com.example.ikr_application.nfirex.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.nfirex.domain.TimePrecisions

@Deprecated("Use your own classes:)")
class MyViewModel : ViewModel() {
    init {
        throw IllegalStateException("Removed class")
    }

    fun timePrecisions(): List<TimePrecisions> {
        throw IllegalStateException("Removed class")
    }

    fun date(): String {
        throw IllegalStateException("Removed class")
    }

    fun elapsedTime(precision: TimePrecisions): String {
        throw IllegalStateException("Removed class")
    }
}