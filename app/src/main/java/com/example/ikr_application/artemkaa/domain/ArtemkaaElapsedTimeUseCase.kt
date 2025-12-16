package com.example.ikr_application.artemkaa.domain

import com.example.ikr_application.artemkaa.data.ArtemkaaRepository

class ArtemkaaElapsedTimeUseCase() {
    fun value(precisions: ArtemkaaTimePrecisions): Long {
        val elapsedTime = ArtemkaaRepository.INSTANCE.artemkaaInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}