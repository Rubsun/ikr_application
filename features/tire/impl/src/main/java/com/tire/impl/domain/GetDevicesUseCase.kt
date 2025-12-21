package com.tire.impl.domain

import com.tire.impl.data.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tire.api.domain.usecases.GetDevicesUseCase
import com.tire.api.domain.models.DeviceInfo

internal class GetDevicesUseCase(
    private val repository: DeviceRepository
) : GetDevicesUseCase {
    override suspend operator fun invoke(filter: String): List<DeviceInfo> = withContext(Dispatchers.IO) {
        repository.getAllDevices(filter).map { dto ->
            DeviceInfo(
                id = dto.id,
                currentTime = dto.currentTime,
                elapsedTime = dto.elapsedTime,
                deviceName = dto.deviceName
            )
        }
    }
}
