package com.example.ikr_application.tire.domain

import com.example.ikr_application.tire.data.DeviceRepository
import com.example.ikr_application.tire.data.models.DeviceInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDevicesUseCase(
    private val repository: DeviceRepository = DeviceRepository.INSTANCE
) {
    suspend operator fun invoke(filter: String): List<DeviceInfo> = withContext(Dispatchers.IO) {
        repository.getAllDevices(filter)
    }
}
