package com.akiko23.impl.domain

import com.akiko23.api.domain.models.TimePrecision
import com.akiko23.api.domain.usecases.ElapsedTimeUseCase
import com.akiko23.impl.data.DeviceRepository

/**
 * Реализация UseCase для получения прошедшего времени.
 */
internal class ElapsedTimeUseCaseImpl(
    private val repository: DeviceRepository
) : ElapsedTimeUseCase {
    override fun value(precision: TimePrecision): Long {
        val elapsedTime = repository.deviceInfo().elapsedTime
        return elapsedTime / precision.divider.inWholeMilliseconds
    }
}

