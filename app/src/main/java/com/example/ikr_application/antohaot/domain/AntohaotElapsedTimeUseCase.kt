package com.example.ikr_application.antohaot.domain

import com.example.ikr_application.antohaot.data.AntohaotRepository

class AntohaotElapsedTimeUseCase() {
    fun value(precisions: AntohaotTimePrecisions): Long {
        val elapsedTime = AntohaotRepository.INSTANCE.antohaotInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}