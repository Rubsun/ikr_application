package com.example.ikr_application.akiko23.domain

import com.example.ikr_application.akiko23.data.Akiko23DeviceRepository

/**
 * Отвечает за преобразование аптайма устройства в нужную точность
 * для экрана akiko23.
 */
class Akiko23ElapsedTimeUseCase {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = Akiko23DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}
