package com.example.ikr_application.rin2396.domain

import com.example.ikr_application.rin2396.data.RinRepository

class RinElapsedTimeUseCase() {
    suspend fun value(precision: RinTimePrecisions): Long {
        val elapsedTime = RinRepository.INSTANCE.rinInfo().elapsedTime
        return elapsedTime / precision.divider.inWholeMilliseconds
    }
}