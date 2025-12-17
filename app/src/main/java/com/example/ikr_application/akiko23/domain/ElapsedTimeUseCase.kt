package com.example.ikr_application.akiko23.domain

import com.example.ikr_application.akiko23.data.Akiko23DeviceRepository
import com.example.ikr_application.akiko23.domain.Akiko23TimePrecision

/**
 * Отвечает за преобразование аптайма устройства в нужную точность
 * для экрана akiko23.
 */
class Akiko23ElapsedTimeUseCase {
    fun value(precisions: Akiko23TimePrecision): Long {
        val elapsedTime = Akiko23DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}
