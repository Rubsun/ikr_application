package com.example.ikr_application.rin2396.domain

import com.example.ikr_application.rin2396.data.RinRepository

class RinElapsedTimeUseCase() {
    fun value(precisions: RinTimePrecisions): Long {
        val elapsedTime = RinRepository.INSTANCE.rinInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}