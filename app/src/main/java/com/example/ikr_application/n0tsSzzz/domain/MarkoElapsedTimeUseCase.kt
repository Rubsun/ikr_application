package com.example.ikr_application.n0tsSzzz.domain

import android.annotation.SuppressLint
import com.example.ikr_application.n0tsSzzz.data.MarkoRepository

class MarkoElapsedTimeUseCase() {
    @SuppressLint("DiscouragedApi")
    fun value(precisions: MarkoTimePrecisions): Long {
        val elapsedTime = MarkoRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}

