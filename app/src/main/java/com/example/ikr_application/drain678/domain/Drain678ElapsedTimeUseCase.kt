package com.example.ikr_application.drain678.domain

import com.example.ikr_application.drain678.data.Drain678Repository

class Drain678ElapsedTimeUseCase() {
    fun value(precisions: Drain678TimePrecisions): Long {
        val elapsedTime = Drain678Repository.INSTANCE.drain678Info().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}