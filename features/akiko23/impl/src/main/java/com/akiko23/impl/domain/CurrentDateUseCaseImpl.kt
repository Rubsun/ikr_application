package com.akiko23.impl.domain

import com.akiko23.api.domain.usecases.CurrentDateUseCase
import com.akiko23.impl.data.DeviceRepository
import java.util.Date

/**
 * Реализация UseCase для получения текущей даты.
 */
internal class CurrentDateUseCaseImpl(
    private val repository: DeviceRepository
) : CurrentDateUseCase {
    override fun date(): Date {
        val timestamp = repository.deviceInfo().currentTime
        return Date(timestamp)
    }
}

