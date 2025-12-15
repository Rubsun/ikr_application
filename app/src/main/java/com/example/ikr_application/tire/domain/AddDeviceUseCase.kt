package com.example.ikr_application.tire.domain

import com.example.ikr_application.tire.data.DeviceRepository
import com.example.ikr_application.tire.data.models.DeviceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddDeviceUseCase(
    private val repository: DeviceRepository = DeviceRepository.INSTANCE
) {
    suspend operator fun invoke(deviceName: String) = withContext(Dispatchers.IO) {
        val info = DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = android.os.SystemClock.elapsedRealtime(),
            deviceName = deviceName
        )
        repository.addDevice(info)
    }
}
