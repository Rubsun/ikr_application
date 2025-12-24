package com.tire.impl.domain

import com.tire.impl.data.DeviceRepository
import com.tire.impl.data.models.DeviceInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tire.api.domain.usecases.AddDeviceUseCase

internal class AddDeviceUseCase(
    private val repository: DeviceRepository
) : AddDeviceUseCase {
    override suspend operator fun invoke(deviceName: String) = withContext(Dispatchers.IO) {
        val info = DeviceInfoDto(
            currentTime = System.currentTimeMillis(),
            elapsedTime = android.os.SystemClock.elapsedRealtime(),
            deviceName = deviceName
        )
        repository.addDevice(info)
    }
}
