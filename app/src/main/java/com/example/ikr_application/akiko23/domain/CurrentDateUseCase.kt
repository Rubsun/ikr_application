package com.example.ikr_application.akiko23.domain

import com.example.ikr_application.akiko23.data.Akiko23DeviceRepository
import java.util.Date

/**
 * Возвращает текущее время в виде Date
 * специально для экрана akiko23.
 */
class Akiko23CurrentDateUseCase {
    fun date(): Date {
        val timestamp = Akiko23DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}
