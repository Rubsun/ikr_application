package com.alexcode69.impl.domain

import com.alexcode69.api.domain.usecases.CurrentDateUseCase
import com.alexcode69.impl.data.DeviceRepository
import java.util.Date

internal class CurrentDateUseCaseImpl(
    private val repository: DeviceRepository
) : CurrentDateUseCase {
    override fun date(): Date {
        val timestamp = repository.deviceInfo().currentTime
        return Date(timestamp)
    }
}

