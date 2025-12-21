package com.akiko23.api.domain.usecases

import com.akiko23.api.domain.models.TimePrecision

/**
 * UseCase для получения прошедшего времени с момента запуска устройства.
 */
interface ElapsedTimeUseCase {
    fun value(precision: TimePrecision): Long
}

