package com.tire.impl.domain

import com.tire.impl.data.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import com.tire.api.domain.usecases.CurrentDateUseCase

internal class CurrentDateUseCase(
    private val repository: DeviceRepository
) : CurrentDateUseCase{
    override suspend operator fun invoke(): Date = withContext(Dispatchers.IO) {
        val timestamp = repository.deviceInfo().currentTime
        Date(timestamp)
    }
}
